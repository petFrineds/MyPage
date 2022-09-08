package petfriends.mypage.controller;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import petfriends.mypage.dto.Payed;
import petfriends.mypage.dto.ReservationStatus;
import petfriends.mypage.model.MyPage;
import petfriends.mypage.repository.MyPageRepository;
import petfriends.mypage.service.MyPageService;

import javax.transaction.Transactional;

@RestController
@RequestMapping("/")
 public class MyPageController {

	 @Autowired
	 MyPageService mypageService;

	 @Autowired
	 MyPageRepository mypageRepository;


	@RequestMapping(method= RequestMethod.GET, path="/mypages/users/{userId}")
	public List<MyPage> findAllByUserId(@PathVariable String userId){
		List<MyPage> mypages =  mypageService.findAllByUserId(userId);

		mypages = sortWalkFirst(mypages);
		return mypages;
	}


	@RequestMapping(method= RequestMethod.GET, path="/mypages/dogwalker/{dogwalkerId}")
	public List<MyPage> findAllByDogwalkerId(@PathVariable String dogwalkerId){
		List<MyPage> mypages =  mypageService.findAllByDogwalkerId(dogwalkerId);

		mypages = sortWalkFirst(mypages);
		return mypages;
	}


	public List<MyPage> sortWalkFirst(List<MyPage> myPages){

		for(int i =0 ; i < myPages.size() ; i++){
			MyPage temp = myPages.get(i);
			if( temp.getStatus().equals(ReservationStatus.START)){
				temp.setSortSeq(1);
			}else{
				temp.setSortSeq(999);
			}
		}

		//sort seq 오름차순으로 정렬
		Collections.sort(myPages, (c1, c2) ->  c1.getSortSeq()-c2.getSortSeq());

		return myPages;
	};
}

 