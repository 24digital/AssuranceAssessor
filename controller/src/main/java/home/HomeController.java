package home;

import Entities.Home;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Marion on 8/29/2014.
 */
@RestController
public class HomeController {

    @RequestMapping("/")
    public Home greeting()
    {
        return new Home("Hello");
    }
}
