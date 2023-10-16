package com.example.mongchi_shop.service;

import com.example.mongchi_shop.dao.ReviewDAO;
import com.example.mongchi_shop.domain.ReviewVO;
import com.example.mongchi_shop.dto.ReviewDTO;
import com.example.mongchi_shop.util.MapperUtil;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public enum ReviewService {
    INSTANCE;
    private ModelMapper modelMapper;
    private ReviewDAO reviewDAO;
    ReviewService() {
        reviewDAO = new ReviewDAO();
        modelMapper = MapperUtil.INSTANCE.getInstance();
    }

    public String getFileName(Part part) {
        /* part 객체로 전달된 이미지 파일로 부터 파일 이름을 추출하기 위한 메서드 */
        String fileName = null;

        // 파일 이름이 들어있는 헤더 영역을 가지고 옴
        String header = part.getHeader("content-disposition");
        log.info("File Header : " + header);

        // 파일 이름이 들어있는 속성 부분의 시작 위치를 가져와 쌍따옴표 사이의 값 부분만 가지고옴
        int start = header.indexOf("filename=");
        fileName = header.substring(start + 10, header.length() - 1);
        log.info("파일명 : " + fileName);
        return fileName;
    }

    public void addReview(ReviewDTO reviewDTO) throws SQLException {
        log.info("addReview()...");

        ReviewVO reviewVO = modelMapper.map(reviewDTO, ReviewVO.class);
        log.info("reviewVO : " + reviewVO);
        reviewDAO.insertReview(reviewVO);
    }

    public List<ReviewVO> getReview(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        log.info("getReview()...");

        int pno = Integer.parseInt(request.getParameter("pno"));

        List<ReviewVO> reviewVOList = reviewDAO.selectReview(pno);
        log.info(reviewVOList);

        // 댓글 중 로그인한 사용자가 작성한 댓글이면 isLogin 값을 true로 변경
        for (ReviewVO reviewVO : reviewVOList) {
            log.info(reviewVO.getEmailId());
           /* log.info(request.getSession().getAttribute("sessionMemberId"));

            if (reviewVO.getEmailId().equals(request.getSession.getAttribute("sessionMemberId")))
            // 댓글 작성자와 로그인한 사용자가 같은 경우
            reviewVO.setLogin(true);*/
        }
    return reviewVOList;
    }

    public List<ReviewDTO> getReviewByEmailId(String emailId) throws Exception {

        log.info("getReviewByEmailId()... ");
        List<ReviewVO> reviewVOList = reviewDAO.selectReviewByEmailId(emailId);
        List<ReviewDTO> reviewDTOList = new ArrayList<>();

        for (ReviewVO reviewVO : reviewVOList) {
            log.info(reviewVO.getEmailId());
            reviewDTOList.add(modelMapper.map(reviewVO,ReviewDTO.class));
        }
        return reviewDTOList;
    }

    public ReviewDTO getReviewByPno(int pno, int rno) throws SQLException {
        log.info("getReviewByPno()...");
        ReviewVO reviewVO = reviewDAO.selecetReviewByPno(pno,rno);
        log.info(reviewVO);
        ReviewDTO reviewDTO = modelMapper.map(reviewVO,ReviewDTO.class);
        log.info("reviewDTO : " + reviewDTO);
        return reviewDTO;
    }

    /* 리뷰 수정 */
    public void modifyReview(ReviewDTO reviewDTO) throws SQLException {
        log.info("modifyReview()... ");
        log.info("reviewDTO : " + reviewDTO);
        ReviewVO reviewVO = modelMapper.map(reviewDTO, ReviewVO.class);

        log.info("reviewVO : " + reviewVO);
        reviewDAO.updateReview(reviewVO);
    }

    /* 리뷰 삭제 */
    public void removeReview(int rno) throws SQLException {
        reviewDAO.deleteReview(rno);
    }


    public void processReview() {
        ReviewVO reviewVO = new ReviewVO();
        // ReviewVO 객체를 생성하고 데이터를 설정하는 로직이 있다면 여기에 추가

        // 디버깅 문을 추가하여 ReviewVO 객체의 상태를 확인
        System.out.println("ReviewVO: " + reviewVO);
        System.out.println("rno: " + reviewVO.getRno());
        System.out.println("emailId: " + reviewVO.getEmailId());
        System.out.println("pno: " + reviewVO.getPno());
        System.out.println("rate: " + reviewVO.getRate());
        System.out.println("content: " + reviewVO.getContent());
    }

}
