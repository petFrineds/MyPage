package petfriends;

import lombok.extern.slf4j.Slf4j;
import petfriends.config.KafkaProcessor;
import petfriends.mypage.dto.*;
import petfriends.mypage.model.MyPage;
import petfriends.mypage.repository.MyPageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class PolicyHandler{
    @StreamListener(KafkaProcessor.INPUT)
    public void onStringEventListener(@Payload String eventString){

    }
    
    @Autowired
    MyPageRepository mypageRepository;

    //예약 신규 등록
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverCreated_(@Payload Created created)
    {
        if(created.isMe()){
            Optional<MyPage> mypageOptional = mypageRepository.findByReservedId(created.getReservedId());

            MyPage myPage = new MyPage();
            myPage.setReservedId(created.getReservedId());
            myPage.setStartTime(created.getStartTime());
            myPage.setEndTime(created.getEndTime());
            myPage.setDogwalkerScheduleId(created.getDogwalkerScheduleId());
            myPage.setDogwalkerId(created.getDogwalkerId());
            myPage.setDogwalkerName(created.getDogwalkerName());
            myPage.setUserId(created.getUserId());
            myPage.setUserName(created.getUserName());
            myPage.setAmount(created.getAmount());
            myPage.setStatus(created.getStatus());

            LocalDateTime current = LocalDateTime.now();
            myPage.setRegDate(java.sql.Timestamp.valueOf(current));
            myPage.setUpdDate(java.sql.Timestamp.valueOf(current));

            if(!mypageOptional.isPresent()) { // 동일 예약건이 없을 때
                mypageRepository.save(myPage);
            }else{
                new RuntimeException("결재완료에 해당하는 예약 번호가 존재하지 않습니다.");
            }
        }
    }


    // 상태변경 - 결재완료
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverPayed_(@Payload Payed payed)
    {
        log.info(">> WheneverPayed --> " + payed.toString() + " // " + payed.getReservedId());

        if(payed.isMe()){
            log.info(">> WheneverPayed --> isMe " + payed.toString() + " // " + payed.getReservedId());

            Optional<MyPage> mypageOptional = mypageRepository.findByReservedId(payed.getReservedId());

            if(mypageOptional.isPresent()) {

                log.info(">> WheneverPayed --> isPresent " + payed.toString() + " // " + payed.getReservedId());

                MyPage mypage = mypageOptional.get();
                mypage.setStatus(ReservationStatus.PAYED);
                mypage.setPayType(payed.getPayType());
                mypage.setAmount(payed.getAmount());
                mypageRepository.save(mypage);
            }
        }
    }

    // 산책 시작
    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverWalkStarted_(@Payload WalkStarted walkStarted) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if(walkStarted.isMe()){
            Optional<MyPage> mypageOptional = mypageRepository.findByReservedId(walkStarted.getReservedId());

            if(mypageOptional.isPresent()) {
                MyPage mypage = mypageOptional.get();
                mypage.setStatus(ReservationStatus.START); // 산책시작

                Date walkStartDate = format.parse(walkStarted.getWalkStartDate());
                mypage.setWalkStartDate(walkStartDate);

                LocalDateTime current = LocalDateTime.now();
                mypage.setUpdDate(java.sql.Timestamp.valueOf(current));

                mypageRepository.save(mypage);
            }
        }
    }

    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverWalkEnded_(@Payload WalkEnded walkEnded) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if(walkEnded.isMe()){
            Optional<MyPage> mypageOptional = mypageRepository.findByReservedId(walkEnded.getReservedId());
            if(mypageOptional.isPresent()) {
                MyPage mypage = mypageOptional.get();
                mypage.setStatus(ReservationStatus.END); // 산책종료

                Date walkEndDate = format.parse(walkEnded.getWalkEndDate());
                mypage.setWalkStartDate(walkEndDate);

                LocalDateTime current = LocalDateTime.now();
                mypage.setUpdDate(java.sql.Timestamp.valueOf(current));

                mypageRepository.save(mypage);

            }


            //에약 취소 추가 해야함
        }
    }


    @StreamListener(KafkaProcessor.INPUT)
    public void wheneverRefunded_(@Payload Refunded refunded) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        if(refunded.isMe()){
            Optional<MyPage> mypageOptional = mypageRepository.findByReservedId(refunded.getReservedId());
            if(mypageOptional.isPresent()) {
                MyPage mypage = mypageOptional.get();
                mypage.setAmount(0.0);
                mypage.setStatus(ReservationStatus.CANCEL);
                LocalDateTime current = LocalDateTime.now();
                mypage.setUpdDate(java.sql.Timestamp.valueOf(current));

                mypageRepository.save(mypage);

            }
        }
    }
}
