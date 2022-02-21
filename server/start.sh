var=/var/lib/docker/volumes/afcb73ec45b678150275f4e99084f88526d6fdacba989d4f700d8156fb67afb8/_data/workspace/test/server/api-module/build/libs/

var2=/var/lib/docker/volumes/afcb73ec45b678150275f4e99084f88526d6fdacba989d4f700d8156fb67afb8/_data/workspace/test/server/myflask/

nohup java -jar ${var}*.jar --logging.file.path=${var} --logging.level.org.hibernate.SQL=DEBUG >> ${var}deploy.log 2>${var}deploy_err.log &

cd

cd $var2

FLASK_APP=main.py flask run --host=0.0.0.0


echo “start”
