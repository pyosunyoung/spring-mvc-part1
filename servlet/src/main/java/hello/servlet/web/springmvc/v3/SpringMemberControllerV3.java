package hello.servlet.web.springmvc.v3;

import hello.servlet.domain.member.Member;
import hello.servlet.domain.member.MemberRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
/**
 * v3
 * Model 도입
 * ViewName 직접 반환
 * @RequestParam 사용
 * @RequestMapping -> @GetMapping, @PostMapping
 */
//v2 코드와 비교해서 어떻게 실무적으로 바뀌었는지 비교분석 ㄱㄱ
@Controller
@RequestMapping("/springmvc/v3/members")
public class SpringMemberControllerV3 {

    private MemberRepository memberRepository = MemberRepository.getInstance();

    @GetMapping("/new-form")
    public String newForm() { //실무 코드
        return "new-form"; // 알아서 view로 삽입됨.
    }
//    public ModelAndView newForm() { // 과거 코드
//        return new ModelAndView("new-form");
//    }

    @PostMapping("/save")
    public String save(@RequestParam("username") String username, //HttpServletRequest request, HttpServletResponse response 이 파라미터에서 이렇게 바꿔줌
                             @RequestParam("age") int age,
                             Model model) throws Exception {

        Member member = new Member(username, age);
        memberRepository.save(member);

        model.addAttribute("member", member); // 모델에 담아주고
        return "save-result";
    }

    @GetMapping
    public String members(Model model) {
        List<Member> members = memberRepository.findAll();

        model.addAttribute("members", members);
        return "members";
    }
}
