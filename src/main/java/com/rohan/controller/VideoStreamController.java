package com.rohan.controller;

import com.rohan.service.VideoStreamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
public class VideoStreamController {

    private final VideoStreamService videoStreamService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public VideoStreamController(VideoStreamService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }

    @GetMapping("/{fileType}/{fileName}")
    public Mono<ResponseEntity<byte[]>> streamVideo(ServerHttpResponse serverHttpResponse, @RequestHeader(value = "Range", required = false) String httpRangeList,
                                                    @PathVariable("fileType") String fileType,
                                                    @PathVariable("fileName") String fileName) {
        logger.info("streaming " + fileName + "." + fileType);
        return Mono.just(videoStreamService.prepareContent(fileName, fileType, httpRangeList));
    }
}
