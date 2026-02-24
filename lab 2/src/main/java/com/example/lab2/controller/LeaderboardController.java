package com.example.lab2.controller;

import com.example.lab2.dto.LeaderboardEntryDto;
import com.example.lab2.dto.LeaderboardScoreRequestDto;
import com.example.lab2.dto.PlayerRankDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@RequestMapping("/leaderboard")
@RestController
public class LeaderboardController {

    private static final String LEADERBOARD_KEY = "lab2:leaderboard";
    private final StringRedisTemplate redisTemplate;

    @PostMapping("/score")
    public LeaderboardEntryDto setScore(@RequestBody LeaderboardScoreRequestDto request) {
        redisTemplate.opsForZSet().add(LEADERBOARD_KEY, request.player(), request.score());
        return new LeaderboardEntryDto(request.player(), request.score());
    }

    @GetMapping("/top")
    public List<LeaderboardEntryDto> top(@RequestParam(defaultValue = "10") int limit) {
        Set<ZSetOperations.TypedTuple<String>> entries =
                redisTemplate.opsForZSet().reverseRangeWithScores(LEADERBOARD_KEY, 0, (long) Math.max(limit, 1) - 1);

        if (entries == null || entries.isEmpty()) {
            return Collections.emptyList();
        }

        return entries.stream()
                .map(it -> new LeaderboardEntryDto(it.getValue(), it.getScore() == null ? 0.0 : it.getScore()))
                .toList();
    }

    @GetMapping("/rank/{player}")
    public PlayerRankDto rank(@PathVariable String player) {
        Long rank = redisTemplate.opsForZSet().reverseRank(LEADERBOARD_KEY, player);
        return new PlayerRankDto(player, rank == null ? -1 : rank + 1);
    }
}
