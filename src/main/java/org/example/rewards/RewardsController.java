package org.example.rewards;

import org.example.rewards.dto.RewardsSummaryResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rewards")
public class RewardsController {

    private final RewardsService rewardsService;

    public RewardsController(RewardsService rewardsService) {
        this.rewardsService = rewardsService;
    }

    @GetMapping("/summary")
    public ResponseEntity<RewardsSummaryResponse> getRewardsSummary() {
        return ResponseEntity.ok(rewardsService.getRewardsSummary());
    }

}
