import uuid

import firebase_admin
import multipart.multipart
import requests
from PIL import ImageFile
from firebase_admin import credentials
from firebase_admin import storage
from rembg.bg import remove
import numpy as np
import io
from PIL import Image
import os
from flask import Flask, request
from uuid import uuid4
import pymysql
from werkzeug.utils import secure_filename

app = Flask(__name__)
if __name__ == '__main__':
    app.run(
        host="0.0.0.0",
        port=5000
    )
app = Flask(__name__)
ImageFile.LOAD_TRUNCATED_IMAGES = True
PROJECT_ID = "iv-blanc"
db = pymysql.connect(host="i6d104.p.ssafy.io", user="ivblanc", passwd="ivblancgumi104", db="ivblanc", charset="utf8")
cur = db.cursor()
cred = credentials.Certificate("iv-blanc-firebase-adminsdk-l6zca-ba0836f40e.json")
default_app = firebase_admin.initialize_app(cred, {
    'storageBucket': f"{PROJECT_ID}.appspot.com"
})
bucket = storage.bucket()  # 기본 버킷 사용


def fileUpload(file):
    blob = bucket.blob(file)
    # new token and metadata 설정
    new_token = uuid4()
    metadata = {"firebaseStorageDownloadTokens": new_token}  # access token이 필요하다.
    blob.metadata = metadata

    # upload file
    blob.upload_from_filename(filename='./' + file, content_type='image/png')
    print(blob.public_url)


@app.route("/", methods=['POST'])
def removebg():
    f = request.files['file']
    fname = secure_filename(f.filename)
    path = os.path.join(fname)
    f.save(path)
    clothId = request.args.get('clothId', type=str)
    # 저장 된 이미지 확인
    output_path = str(uuid.uuid1()) + ".png"
    print(output_path)
    f = np.fromfile(path)
    result = remove(f)
    img = Image.open(io.BytesIO(result)).convert("RGBA")
    img = img.resize((192, 256))
    img.save(output_path, quality=70)
    fileUpload(output_path)

    newpath = "https://storage.googleapis.com/iv-blanc.appspot.com/" + output_path
    cur.execute("UPDATE clothes set url = %s where clothes_id = %s", [newpath,clothId])
    cur.fetchall()
    db.commit()
    db.close()
    return "finish remove bg url is " + newpath
