---------------------------------------------------
1. maria 설치 및 테이블 생성(예제에는 id/passwd : root/1234 , 변경은 application.yml에서 하면 됨. )
---------------------------------------------------
테이블스페이스 : create database petfriends;

mariadb 5.5 버전에서는 TIMESTAMP를 한 컬럼만 사용이 가능하고 NOW() 함수를 쓸수가 없다.... 

CREATE TABLE mypage (
id BIGINT(20) NOT NULL AUTO_INCREMENT PRIMARY KEY,
reserved_id BIGINT(20) NOT NULL ,

start_time DATETIME NULL DEFAULT NULL,
end_time DATETIME NULL DEFAULT NULL,

dogwalker_schedule_id BIGINT(20) NOT NULL,
dogwalker_id NVARCHAR(50) NULL,
dogwalker_name NVARCHAR(50) NULL,

status NVARCHAR(20) NOT NULL DEFAULT '1',

user_id NVARCHAR(50) DEFAULT NULL,
user_name NVARCHAR(50) DEFAULT NULL,
amount DOUBLE NULL DEFAULT NULL,

pay_type NVARCHAR(50) DEFAULT NULL,

walk_id BIGINT(20),
walk_start_date DATETIME,
walk_end_date DATETIME,

reg_date DATETIME,
upd_date DATETIME
) COLLATE='utf8mb4_general_ci' ENGINE=InnoDB ;
 
insert샘플:
insert into mypage (id, reserved_id, start_time, end_time, dogwalker_schedule_id, dogwalker_id, dogwalker_name, user_id, user_name, amount, status, pay_type, walk_start_date, walk_end_date) 
values (2, 20, "2022-08-22 19:00:00", "2022-08-22 21:00:00", 1, "mimi_id", "mimi", "geny_id", "geny",  40000, "REQUEST", "CARD", "2022-08-22 18:50:00", "2022-08-22 22:03:00");

insert into mypage (id, reserved_id, start_time, end_time, dogwalker_schedule_id, dogwalker_id, dogwalker_name, user_id, user_name, amount, status, pay_type, walk_start_date, walk_end_date)
values (3, 20, "2022-08-22 19:00:00", "2022-08-22 21:00:00", 1, "mimi_id", "mimi", "geny_id", "geny",  40000, "PAYED", "CARD", "2022-08-22 18:50:00", "2022-08-22 22:03:00");

insert into mypage (id, reserved_id, start_time, end_time, dogwalker_schedule_id, dogwalker_id, dogwalker_name, user_id, user_name, amount, status, pay_type, walk_start_date, walk_end_date)
values (4, 20, "2022-08-22 19:00:00", "2022-08-22 21:00:00", 1, "mimi_id", "mimi", "geny_id", "geny",  40000, "START", "CARD", "2022-08-22 18:50:00", "2022-08-22 22:03:00");

insert into mypage (id, reserved_id, start_time, end_time, dogwalker_schedule_id, dogwalker_id, dogwalker_name, user_id, user_name, amount, status, pay_type, walk_start_date, walk_end_date)
values (5, 20, "2022-08-22 19:00:00", "2022-08-22 21:00:00", 1, "mimi_id", "mimi", "geny_id", "geny",  40000, "END", "CARD", "2022-08-22 18:50:00", "2022-08-22 22:03:00");

insert into mypage (id, reserved_id, start_time, end_time, dogwalker_schedule_id, dogwalker_id, dogwalker_name, user_id, user_name, amount, status, pay_type, walk_start_date, walk_end_date)
values (6, 20, "2022-08-22 19:00:00", "2022-08-22 21:00:00", 1, "mimi_id", "mimi", "geny_id", "geny",  40000, "DAILY_WRITED", "CARD", "2022-08-22 18:50:00", "2022-08-22 22:03:00");

---------------------------------------------------  
2. 배포 방법
---------------------------------------------------  
git clone https://github.com/petFrineds/MyPage.git
mvn install
aws ecr create-repository --repository-name mypage-backend --image-scanning-configuration scanOnPush=true --region us-west-2
docker build -t mypage-backend .
docker tag mypage-backend:latest 811288377093.dkr.ecr.$AWS_REGION.amazonaws.com/mypage-backend:latest
docker push 811288377093.dkr.ecr.us-west-2.amazonaws.com/mypage-backend:latest
aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin 811288377093.dkr.ecr.us-west-2.amazonaws.com/

-- 여기서 부터는 ec2-user 사용
kubectl apply -f manifests/mypage-deployment.yaml
kubectl get deploy
kubectl apply -f manifests/mypage-service.yaml
kubectl get service
kubectl get ingress

--------------------------------------------------  
3. Payment(mariadb), Shop(hsqldb) 실행 및 테스트  
--------------------------------------------------  
1) Payment에서 아래와 같이 api 통해 데이터 생성하면, mariadb[payment테이블]에 데이터 저장되고, message publish.  
    - 데이터생성(postman사용) : POST http://localhost:8082/payments/   
                              { "reservedId": "202203271311", "userId": "soya95", "amount": "10000", "payDate": "2019-03-10 10:22:33.102" }  

    - 조회 : GET http://localhost:8082/payments/1  

3) Shop에서 message 받아와 저장 ( 아래 PloycyHandler.java가 실행됨 )  

--------------------------------------------------  
4. 구조   
   -controller  
   -service  
   -repository  
   -dto  
   -model  
   -config : KafkaProcessor.java, WebConfig.java(CORS적용)  
--------------------------------------------------  
5. API  
   해당ID의 결제내역조회 : GET http://localhost:8082/payments/{userId}   
--------------------------------------------------  
