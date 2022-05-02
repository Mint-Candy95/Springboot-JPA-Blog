package com.cos.blog.test;

import com.cos.blog.model.RoleType;
import com.cos.blog.model.User;
import com.cos.blog.repository.UserRepository;

import java.util.List;
import java.util.function.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;

//html파일이 아닌 data리턴해주는 controller = RestController
@RestController
public class DummyControllerTest {

    @Autowired //의존성 주입(DI)
    private UserRepository userRepository;


    @DeleteMapping("/dummy/user/{id}")
    public String delete(@PathVariable int id){
        try {
            userRepository.deleteById(id);
        }catch (EmptyResultDataAccessException e){
            return "삭제에 실패하였습니다 해당 id는 DB에 없습니다.";
        }

        return "삭제되었습니다. id : "+id;
    }

    //save함수는 id를 전달달하지 않으면insert를 해주고
    //id전달하면 해당 id에 대한 데이터 있으면 update
    //없으면 insert
    //email, password
    @Transactional //함수 종료시에 자동 commit
    @PutMapping("/dummy/user/{id}")
    public User updateUser(@PathVariable int id, @RequestBody User requestUser){
        //Json data -> java object 변화하여 받아준다.(MessageConverter jackson라이브러리)

        System.out.println("id:"+id);
        System.out.println("password "+requestUser.getPassword());
        System.out.println("email "+requestUser.getEmail());

        User user = userRepository.findById(id).orElseThrow(()->{
            return new IllegalArgumentException("수정에 실패하엿습니다.");
        });
        user.setPassword(requestUser.getPassword());
        user.setEmail(requestUser.getEmail());

        // userRepository.save(user);

        //더티 체킹킹
       return null;
    }

    //http://localhost:8000/blog/dummy/user
    @GetMapping("/dummy/users")
    public List<User> list(){
        return userRepository.findAll();
    }

    //한페이지당 2건의 데이터 리턴
    @GetMapping("/dummy/user")
    public List<User> pageList(@PageableDefault(size=2, sort = "id", direction = Sort.Direction.DESC)Pageable pageable){
        Page<User> pagingUser = userRepository.findAll(pageable);

        List<User> users = pagingUser.getContent();
        return users;
    }
    //{id} 주소로 파라미터를 전달 받을수 있다.
    //http://localhost:8000/blog/dummy/user/3
    @GetMapping("/dummy/user/{id}")
    public User detail(@PathVariable int id){
        // user/4를 찾으면 내가 db에서 못찾으며 user는 null이됨
        //그럼 return null이 되는데 프로그램에 문제가 생긴다
        //optional로 user객체를 감싸서 가져올테니 null 판단 후 return해
        User user = userRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("해당 유저는 없습니다. id:" +id);
        });
        //요청 = 웹브라우저
        //user 객체 = 자바 오브젝트
        // 변환 (웹브라우저가 이해할수 있는 데이터)->json(Gson 라이브러리)
        // 스프링부트 = MessageConverter가 응답시에 자동 작동
        //만약 자바 오베즉트를 리턴하게 되면 MC가 jackson라이브러리 호출하여
        //user오브젝트를 json으로 변환하여 브라우저에게 던져준다.
        return user;
    }


    //http://localhost:8000/blog/dummy/join(요청)
    //http의 body에 username, password, email데이터 가지고 요청
    @PostMapping("/dummy/join")
    public String join(User user){ //key = value
        System.out.println("id : " + user.getId());
        System.out.println("usernaem : "+user.getUsername());
        System.out.println("password : "+user.getPassword());
        System.out.println("email : "+user.getEmail());
        System.out.println("Role : "+user.getRole());
        System.out.println("createDate : "+user.getCreateDate());

        user.setRole(RoleType.USER);
        userRepository.save(user);
        return "회원가입이 완료되었습니다.";
    }

}
