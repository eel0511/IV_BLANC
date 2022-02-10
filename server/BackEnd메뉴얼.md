## Server
---
java 1.8 
mysql (springboot연결은 mariadb)
Intellij Ultimate 2021.3.1

Python=3.8 pytorch=[>=1.6.0] cudatoolkit=[>=9.2]
mysql(flask연결은 pymysql)

firebase json파일은 모두 서버파일에 들어있음( iv-blanc-firebase-adminsdk-l6zca-232ca5420a.json )

---
### spring boot

#### application-alpha.yml 

1. datasource의 데이터베이스 연결

2. server의 포트, 도메인 설정

3. Swagger 호스트 설정
4. firebase-config에 friebase json파일 위치를 입력
5. 기타 필요한 설정

#### ClothesController 

sendurl은 일반 플라스크 서버의 주소:포트

sendAiurl은 ai처리하는 플라스크 서버의 주소:포트로 설정한다

#### FileService

DOWNLOAD_URL에 firebase에 올라간 사진이 저장되는 GCP경로를 쓴다

bucket이름 등 firebase 관련 설정을 해준다

#### FcmService

API_URL 등 fcm 관련 설정을 해준다


### Flask 서버

---

myflask안의 main.py(spring의 /api/clothes/add 에 쓰임 )

1. 데이터베이스 설정
2. Firebase 설정



flask_AI 안의 app.py (스프링의 /api/clothes/beta 에 쓰임)


1. Firebase 설정

VITON-HD 설정

https://github.com/shadow2496/VITON-HD



---
### 서버 구동

위의 3가지 서버를 모두 설정 후에

flask_AI 안의 Flask 서버는 flask run으로 구동



springboot 서버 로컬에서 열 경우  Gradle의 cleanQuerydslSourcesDir , bootRun 차례대로 실행


springboot 서버와 myflask안의 Flask서버는 aws에서 빌드 할 경우

i6d104.p.ssafy.io:9090
ivblanc // ivblanc
test 파이프 라인 실행시 빌드


이렇게 하면 jar 파일이 생성된다.

이것을 다음의 start.sh 쉘파일을 만들어서 실행하면 서버가 구동된다.

```
#현재 우리 도커 젠킨스의 jar 빌드 경로
var=/var/lib/docker/volumes/afcb73ec45b678150275f4e99084f88526d6fdacba989d4f700d8156fb67afb8/_data/workspace/test/server/api-module/build/libs/

#myflask의 위치
var2=/var/lib/docker/volumes/afcb73ec45b678150275f4e99084f88526d6fdacba989d4f700d8156fb67afb8/_data/workspace/test/server/myflask/

#스프링 백그라운드 실행

nohup java -jar ${var}*.jar --logging.file.path=${var} --logging.level.org.hibernate.SQL=DEBUG >> ${var}deploy.log 2>${var}deploy_err.log &

cd

cd $var2

#플라스크 실행
FLASK_APP=main.py flask run --host=0.0.0.0


echo “start”
```



플라스크서버는 포그라운드에서 돌고 스프링은 백그라운드에서 돈다.

종료는 현재 실행중 인 플라스크는 컨트롤+C 커맨드로 종료하고 자바는 백그라운드에서 kill해주면 종료된다.



다음과같은 stop.sh 쉘을 이용할 수 있다.

```
#!/bin/sh

PID=`ps  | grep java | awk '{print $1}'`
if [ -n "$PID" ]
then
  echo "=====spring is running at" $PID "Shutdown spring now"
  sudo kill -9 $PID
else
  echo "=====spring isn't running====="
fi

```
