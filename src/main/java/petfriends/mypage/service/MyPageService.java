package petfriends.mypage.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import petfriends.mypage.model.MyPage;
import petfriends.mypage.repository.MyPageRepository;

@Service
public class MyPageService {
	 
	 @Autowired
	 MyPageRepository mypageRepository;

	public List<MyPage> findAllByUserId(String userId) {

		return mypageRepository.findAllByUserId(userId);
	}

	public List<MyPage> findAllByDogwalkerId(String dogwalkerId) {

		return mypageRepository.findAllByDogwalkerId(dogwalkerId);
	}

}

