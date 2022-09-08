package petfriends.mypage.service;

import java.util.*;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import petfriends.mypage.model.MyPage;
import petfriends.mypage.repository.MyPageRepository;

@Service
@Slf4j
public class MyPageService {
	 
	 @Autowired
	 MyPageRepository mypageRepository;

	public List<MyPage> findAllByUserId(String userId) {

		List<MyPage> myPages = mypageRepository.findAllByUserId(userId);
		return myPages;
	}


	public List<MyPage> findAllByDogwalkerId(String dogwalkerId) {

		return mypageRepository.findAllByDogwalkerId(dogwalkerId);
	}

}

