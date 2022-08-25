package petfriends.mypage.controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.util.UriComponentsBuilder;
import petfriends.mypage.dto.Payed;
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
	public ResponseEntity<List<MyPage>> findAllByUserId(@PathVariable String userId){
		List<MyPage> mypages =  mypageService.findAllByUserId(userId);
		if(!mypages.isEmpty()){
			return new ResponseEntity<List<MyPage>>(mypages, HttpStatus.OK);
		}
		//내용 없을 때
		return new ResponseEntity<List<MyPage>>(mypages, HttpStatus.NO_CONTENT);
	}


	@RequestMapping(method= RequestMethod.GET, path="/mypages/dogwalker/{dogwalkerId}")
	public ResponseEntity<List<MyPage>> findAllByDogwalkerId(@PathVariable String dogwalkerId){
		List<MyPage> mypages =  mypageService.findAllByDogwalkerId(dogwalkerId);
		if(!mypages.isEmpty()){
			return new ResponseEntity<List<MyPage>>(mypages, HttpStatus.OK);
		}
		//내용 없을 때
		return new ResponseEntity<List<MyPage>>(mypages, HttpStatus.NO_CONTENT);
	}

}

 