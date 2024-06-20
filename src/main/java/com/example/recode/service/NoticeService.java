package com.example.recode.service;

import com.example.recode.dto.NoticeDto;
import com.example.recode.domain.Notice;
import com.example.recode.dto.NoticeRequest;
import com.example.recode.dto.NoticeViewResponse;
import com.example.recode.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserService userService;


    public List<NoticeDto> getAllNotices() {
        return noticeRepository.findAll().stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    public NoticeDto convertEntityToDto(Notice notice) {
        NoticeDto noticeDTO = new NoticeDto();
        noticeDTO.setNoticeId(notice.getNoticeId());
        noticeDTO.setUsername(userService.findById(notice.getUserId()).getUsername());
        noticeDTO.setNoticeCreateDate(notice.getNoticeCreateDate());
        noticeDTO.setNoticeTitle(notice.getNoticeTitle());
        noticeDTO.setNoticeContent(notice.getNoticeContent());
        noticeDTO.setNoticeViews(notice.getNoticeViews());
        return noticeDTO;
    }

    @Transactional
    public Notice save(NoticeRequest dto, Principal principal) {

        if(dto.getNoticeId() != null) {
            return findById(dto.getNoticeId()).update(dto);
        }
        return noticeRepository.save(Notice.builder()
                .noticeId(dto.getNoticeId())
                .userId(userService.getUserId(principal))
                .noticeTitle(dto.getNoticeTitle())
                .noticeContent(dto.getNoticeContent())
                .build());
    }

    public Notice findById(Long noticeId) {
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("not found notice"));
    }

    public List<Notice> findAll() {
        return noticeRepository.findAll();
    }

    public void deleteById(Long noticeId) {
        noticeRepository.deleteById(noticeId);
    }

    public List<NoticeViewResponse> getAllNoticeInfo(){

        List<NoticeViewResponse> list = new ArrayList<>();

        findAll().forEach(notice -> list.add(new NoticeViewResponse(notice, userService.getUsername(notice.getUserId()))));

        return list;
    }
    @Transactional
    public Notice updateView(Long noticeId) {
        return findById(noticeId).updateViews();
    }
}