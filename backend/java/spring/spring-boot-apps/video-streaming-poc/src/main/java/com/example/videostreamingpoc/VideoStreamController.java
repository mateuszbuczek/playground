package com.example.videostreamingpoc;

import lombok.SneakyThrows;
import org.springframework.core.io.UrlResource;
import org.springframework.core.io.support.ResourceRegion;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRange;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.MediaTypeFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/videos")
public class VideoStreamController {

  @SneakyThrows
  @GetMapping("/{name}")
  public ResponseEntity<ResourceRegion> getVideo(
          @RequestHeader HttpHeaders httpHeaders, @PathVariable String name) {
    UrlResource video = new UrlResource("classpath:video/" + name);
    ResourceRegion resourceRegion = getResourceRegion(video, httpHeaders);
    return ResponseEntity.status(HttpStatus.PARTIAL_CONTENT)
        .contentType(
            MediaTypeFactory.getMediaType(video).orElse(MediaType.APPLICATION_OCTET_STREAM))
        .body(resourceRegion);
  }

  @SneakyThrows
  private ResourceRegion getResourceRegion(UrlResource video, HttpHeaders httpHeaders) {
    long contentLength = video.contentLength();
    HttpRange range = firstOrNull(httpHeaders.getRange());

    if (range != null) {
      long start = range.getRangeStart(contentLength);
      long end = range.getRangeEnd(contentLength);
      long rangeLength = Math.min(1 * 1024 * 1024, end - start + 1);
      return new ResourceRegion(video, start, rangeLength);
    } else {
      long rangeLength = Math.min(1 * 1024 * 1024, contentLength);
      return new ResourceRegion(video, 0, rangeLength);
    }
  }

  private <T> T firstOrNull(List<T> list) {
    return list.size() == 1 ? list.get(0) : null;
  }
}
