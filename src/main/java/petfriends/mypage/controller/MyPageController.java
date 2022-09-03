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
	public List<MyPage> findAllByUserId(@PathVariable String userId){
		List<MyPage> mypages =  mypageService.findAllByUserId(userId);

		return mypages;
	}


	@RequestMapping(method= RequestMethod.GET, path="/mypages/dogwalker/{dogwalkerId}")
	public List<MyPage> findAllByDogwalkerId(@PathVariable String dogwalkerId){
		List<MyPage> mypages =  mypageService.findAllByDogwalkerId(dogwalkerId);

		return mypages;
	}

}

 