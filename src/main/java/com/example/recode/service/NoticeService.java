package com.example.recode.service;

import com.example.recode.dto.NoticeDto;
import com.example.recode.domain.Notice;
import com.example.recode.dto.NoticeRequest;
import com.example.recode.dto.NoticeViewResponse;
import com.example.recode.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final UserService userService;


    public Page<Notice> getAllNotices(Pageable pageable) {
        return noticeRepository.findAll(pageable);
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
    public Notice save(NoticeRequest dto, Principal principal) { // 공지사항 등록&수정

        if(dto.getNoticeId() != null) {
            return findById(dto.getNoticeId()).update(dto); // 공지사항 수정
        }
        return noticeRepository.save(Notice.builder()
                .noticeId(dto.getNoticeId())
                .userId(userService.getUserId(principal))
                .noticeTitle(dto.getNoticeTitle())
                .noticeContent(dto.getNoticeContent())
                .build()); // 공지사항 등록
    }

    public Notice findById(Long noticeId) { // noticeId로 Notice 가져오기
        return noticeRepository.findById(noticeId)
                .orElseThrow(() -> new IllegalArgumentException("not found notice"));
    }

    public List<Notice> findAll() { // List<Notice> 가져오기
        return noticeRepository.findAll();
    }

    public void deleteById(Long noticeId) { // noticeId로 Notice 삭제
        noticeRepository.deleteById(noticeId);
    }

    public List<NoticeViewResponse> getAllNoticeInfo() { // List<NoticeViewResponse> 가져오기

        List<NoticeViewResponse> list = new ArrayList<>();

        findAll().forEach(notice -> list.add(new NoticeViewResponse(notice, userService.getUsername(notice.getUserId()))));

        return list;
    }
    @Transactional
    public Notice updateView(Long noticeId) { // 조회수 증가
        return findById(noticeId).updateViews();
    }

//    public void processInteger(Integer to) {
//        if (to != null) {
//            int intValue = to.intValue();
//            // intValue를 사용하는 코드 작성
//        } else {
//            // to가 null인 경우 처리할 로직 추가
//        }
//    }

    public Page<NoticeViewResponse> noticeViewList(Pageable pageable) { // 페이징 처리한 Page<NoticeViewResponse> 가져옴
        Page<Notice> noticeList = noticeRepository.findAll(pageable); // 페이징 처리한 Page<Notice>
        Page<NoticeViewResponse> noticeViewList = noticeList.map(notice -> new NoticeViewResponse(notice, userService.getUsername(notice.getUserId())));
        return noticeViewList;
    }

    public Page<NoticeViewResponse> noticeViewTitleSearchList(String searchKeyword, Pageable pageable) { // 제목으로 검색해서 페이징 처리한 Page<NoticeViewResponse> 가져옴
        Page<Notice> noticeSearchList = noticeRepository.findByNoticeTitleContaining(searchKeyword, pageable); // 제목으로 검색해서 페이징 처리한 Page<Notice>
        Page<NoticeViewResponse> noticeViewSearchList = noticeSearchList.map(notice -> new NoticeViewResponse(notice, userService.getUsername(notice.getUserId())));
        return noticeViewSearchList;
    }

    public void deleteByIds(List<Long> noticeIds) { // noticeIds 리스트로 Notice 삭제
        for (Long noticeId : noticeIds) {
            noticeRepository.deleteById(noticeId);
        }
    }
}

