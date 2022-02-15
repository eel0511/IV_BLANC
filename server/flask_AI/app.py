from flask import Flask, request
from PIL import ImageFile
import firebase_admin
from firebase_admin import credentials
from firebase_admin import storage
from werkzeug.utils import secure_filename
from uuid import uuid4
import os
import cv2
import numpy as np
import subprocess
import test
import requests
from subprocess import Popen, PIPE
from subprocess import check_output
app = Flask(__name__)
ImageFile.LOAD_TRUNCATED_IMAGES = True

PROJECT_ID = "iv-blanc"
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

def urltoimg(img_data_url):
    save_path='./datasets/test/cloth/'
    full_path = "{}{}".format(save_path, img_data_url[img_data_url.rfind('/')+1:])
    print("full_path : ",full_path)
    v_img = requests.get(img_data_url).content

    with open(full_path, 'wb') as f: #wb: 쓰기 파이러니
        f.write(v_img)
    return img_data_url[img_data_url.rfind('/')+1:]
@app.route("/url",methods=['POST'])
def start2():
    url = request.form.get('url',type=str)
    img_data_url=url
    fname= urltoimg(img_data_url)
    path = os.path.join("./datasets/test/cloth/" + fname)

    img = cv2.imread("./datasets/test/cloth/"+fname)
    im_in = cv2.resize(img, (192, 256))
    cv2.imwrite(path, im_in)
    img = cv2.imread("./datasets/test/cloth/" + fname, cv2.IMREAD_GRAYSCALE)
    print(img.size)

    # Threshold.
    # Set values equal to or above 220 to 0.
    # Set values below 220 to 255.
    th, im_th = cv2.threshold(img, 230, 255, cv2.THRESH_BINARY_INV);
    # Copy the thresholded image.
    im_floodfill = im_th.copy()

    # Mask used to flood filling.
    # Notice the size needs to be 2 pixels than the image.
    h, w = im_th.shape[:2]
    mask = np.zeros((h + 2, w + 2), np.uint8)

    # Floodfill from point (0, 0)
    cv2.floodFill(im_floodfill, mask, (0, 0), 255);

    # Invert floodfilled image
    im_floodfill_inv = cv2.bitwise_not(im_floodfill)

    # Combine the two images to get the foreground.
    im_out = im_th | im_floodfill_inv

    # Display images.
    maskpath = os.path.join("./datasets/test/cloth-mask/"+fname)
    # cv2.imshow("Foreground", im_out)
    cv2.waitKey(0)

    im_out = cv2.resize(im_out, (192, 256))
    cv2.imwrite(maskpath, im_out)
    pair = open("datasets/test_pairs.txt",'w')
    pair.write("07573_00.jpg "+fname)
    pair.close()
    test.main()
    fileUpload("results/su/07573_"+fname)
    return "https://storage.googleapis.com/iv-blanc.appspot.com/results/su/07573_"+fname
@app.route("/",methods=['POST'])
def start():
    f = request.files['file']
    fname = secure_filename(f.filename)
    path = os.path.join("./datasets/test/cloth/"+fname)
    f.save(path)
    img = cv2.imread("./datasets/test/cloth/"+fname)
    im_in = cv2.resize(img, (192, 256))
    cv2.imwrite(path, im_in)
    img = cv2.imread("./datasets/test/cloth/" + fname, cv2.IMREAD_GRAYSCALE)
    print(img.size)

    # Threshold.
    # Set values equal to or above 220 to 0.
    # Set values below 220 to 255.
    th, im_th = cv2.threshold(img, 230, 255, cv2.THRESH_BINARY_INV);
    # Copy the thresholded image.
    im_floodfill = im_th.copy()

    # Mask used to flood filling.
    # Notice the size needs to be 2 pixels than the image.
    h, w = im_th.shape[:2]
    mask = np.zeros((h + 2, w + 2), np.uint8)

    # Floodfill from point (0, 0)
    cv2.floodFill(im_floodfill, mask, (0, 0), 255);

    # Invert floodfilled image
    im_floodfill_inv = cv2.bitwise_not(im_floodfill)

    # Combine the two images to get the foreground.
    im_out = im_th | im_floodfill_inv

    # Display images.
    maskpath = os.path.join("./datasets/test/cloth-mask/"+fname)
    # cv2.imshow("Foreground", im_out)
    cv2.waitKey(0)

    im_out = cv2.resize(im_out, (192, 256))
    cv2.imwrite(maskpath, im_out)
    pair = open("datasets/test_pairs.txt",'w')
    pair.write("07573_00.jpg "+fname)
    pair.close()
    test.main()
    fileUpload("results/su/07573_"+fname)
    return "https://storage.googleapis.com/iv-blanc.appspot.com/results/su/07573_"+fname