package com.example.mongchi_shop.dao;

import com.example.mongchi_shop.domain.ReviewVO;
import lombok.Cleanup;
import lombok.extern.log4j.Log4j2;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
public class ReviewDAO {

    /* 리뷰 작성 */
    public boolean insertReview(ReviewVO reviewVO) throws SQLException {
        log.info("insertReview()...");
        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        String sql = "INSERT INTO review (emailId, pno, rate, content, addDate, fileName) VALUES(?,?,?,?,now(),?)";
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,reviewVO.getEmailId());
        preparedStatement.setInt(2, reviewVO.getPno());
        preparedStatement.setInt(3, reviewVO.getRate());
        preparedStatement.setString(4,reviewVO.getContent());
        preparedStatement.setString(5, reviewVO.getFileName());

        return preparedStatement.executeUpdate() == 1;
    }

    /* 리뷰 목록 출력 */
    public List<ReviewVO> selectReview(int pno) throws SQLException {
        log.info("selectReview()...");
        log.info("pno : " + pno);

        List<ReviewVO> reviewVOList = new ArrayList<>();
        String sql = "SELECT * FROM `review` WHERE `pno` = ? ORDER BY rno DESC";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, pno);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()){
            ReviewVO reviewVO = ReviewVO.builder()
                    .rno((resultSet.getInt("rno")))
                    .emailId(resultSet.getString("emailId"))
                    .pno(resultSet.getInt("pno"))
                    .rate(resultSet.getInt("rate"))
                    .content(resultSet.getString("content"))
                    .addDate(resultSet.getString("addDate"))
                    .fileName(resultSet.getString("fileName"))
                    .build();
            reviewVOList.add(reviewVO);
        }
        return reviewVOList;
    }

    /* 리뷰를 ByPno를 통해 검색 */
    public ReviewVO selecetReviewByPno(int pno, int rno) throws SQLException {
        log.info("selectByPno()...");
        String sql = "SELECT * FROM `review` WHERE pno = ? AND rno = ? ";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,pno);
        preparedStatement.setInt(2,rno);
        @Cleanup ResultSet resultSet = preparedStatement.executeQuery();

        ReviewVO reviewVO = null;
        if (resultSet.next()) {
            reviewVO = ReviewVO.builder()
                    .rno(rno)
                    .emailId(resultSet.getString("emailId"))
                    .pno(pno)
                    .rate(resultSet.getInt("rate"))
                    .content(resultSet.getString("content"))
                    .addDate(resultSet.getString("addDate"))
                    .fileName(resultSet.getString("fileName"))
                    .build();
        }
        return reviewVO;
    }

    /* 리뷰 삭제 */
    public boolean deleteReview(int rno) throws SQLException {
        log.info("deleteReview()...");
        String sql = "DELETE FROM `review` WHERE `rno` = ?";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1,rno);

        return preparedStatement.executeUpdate() == 1;

    }

    /* 리뷰 수정 */
    public void updateReview(ReviewVO reviewVO) throws SQLException {
        log.info("updateReview()...");
        String sql = "UPDATE review SET content = ? , addDate = now(), rate = ?, fileName = ? WHERE rno = ? ";

        @Cleanup Connection connection = ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1,reviewVO.getContent());
        preparedStatement.setInt(2,reviewVO.getRate());
        preparedStatement.setString(3,reviewVO.getFileName());
        preparedStatement.setInt(4,reviewVO.getRno());

        preparedStatement.executeUpdate();

    }

}
