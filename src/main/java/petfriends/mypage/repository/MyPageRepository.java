package petfriends.mypage.repository;

import java.util.List;
import java.util.Optional;

import org.aspectj.apache.bcel.classfile.Module;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import petfriends.mypage.model.MyPage;

public interface MyPageRepository extends JpaRepository<MyPage, Long> {


    List<MyPage> findAllByUserId(String userId);
    List<MyPage> findAllByDogwalkerId(String dogwalkerId);
    Optional<MyPage> findByReservedId(Long reservedId);

    Optional<MyPage> findByWalkId(Long walkId);
    MyPage save(MyPage myPage);



}
