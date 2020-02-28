package dev.hateml.app;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class DataController {

    @GetMapping("/")
    public String get(Model model) {
        List<CoronavirusDataService.CoronavirusDataRow> currentStats =
                CoronavirusDataService.getCurrentStats();
        int totalCases = currentStats.stream()
                .mapToInt(CoronavirusDataService.CoronavirusDataRow::getLatestTotalCases)
                .sum();
        int totalNewCases = currentStats.stream()
                .mapToInt(CoronavirusDataService.CoronavirusDataRow::getDiffFromPrevDay)
                .sum();

        model.addAttribute("data", currentStats);
        model.addAttribute("totalCases", totalCases);
        model.addAttribute("totalNewCases", totalNewCases);
        return "home";
    }
}
