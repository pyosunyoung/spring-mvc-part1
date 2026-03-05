package hello.springmvc.basic.reponse;


import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {
        ModelAndView mav = new ModelAndView("response/hello").addObject("data", "Hello World");
        return mav;
    }

    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) {
        model.addAttribute("data", "Hello World");
        return "response/hello";
    }

    @RequestMapping("/response/hello") // 경로의 이름과 return값이 동일하면 생략 가능 근데 이건 가독성 저하
    public void responseViewV3(Model model) {
        model.addAttribute("data", "Hello World");
    }
}
