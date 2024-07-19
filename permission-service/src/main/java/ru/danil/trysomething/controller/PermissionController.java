package ru.danil.trysomething.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.danil.trysomething.service.PermitService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/permissions")
public class PermissionController {


    @Autowired
    private final PermitService permitService;

    @PostMapping("/startWorkflow")
    public String createOrder(@RequestParam("id") String id) {
        permitService.startWorkflow(id);
        return "Permit started";
    }

    @PostMapping("/firstPermit")
    public String orderAccepted(@RequestParam("id") String id) {
        permitService.firstPermit(id);
        return "First Permit Accepted";
    }

    @PostMapping("/secondPermit")
    public String orderPickedUp(@RequestParam("id") String id) {
        permitService.secondPermit(id);
        return "Second Permit Accepted";
    }

    @PostMapping("/thirdPermit")
    public String orderDelivered(@RequestParam("id") String id) {
        permitService.thirdPermit(id);
        return "Third Permit Accepted";
    }
}
