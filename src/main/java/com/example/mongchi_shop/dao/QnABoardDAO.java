package com.example.mongchi_shop.dao;

import com.example.mongchi_shop.domain.QnABoardVO;
import lombok.Cleanup;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class QnABoardDAO {

    public int getQnAListCount(int pno) throws Exception {

        String sql="select count(*) from qna_board where pno=?";
        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1, pno);
        @Cleanup ResultSet resultSet=preparedStatement.executeQuery();

        int cnt=0;
        if(resultSet.next()) {
            cnt=resultSet.getInt(1);
            System.out.println("dao cnt: "+cnt);
        }
        return cnt;
    }
    public int getQnAListCountEmail(String eamilId) throws Exception {

        String sql="select count(*) from qna_board where emailId=?";
        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1, eamilId);
        @Cleanup ResultSet resultSet=preparedStatement.executeQuery();

        int cnt=0;
        if(resultSet.next()) {
            cnt=resultSet.getInt(1);
            System.out.println("dao cnt: "+cnt);
        }
        return cnt;
    }

    public QnABoardVO selectQnABoardByQno(int pno, int qno) throws Exception {
        // qna 세부 페이지
        String sql="select * from qna_board where pno=? and qno=?";
        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1, pno);
        preparedStatement.setInt(2, qno);
        @Cleanup ResultSet resultSet=preparedStatement.executeQuery();

        QnABoardVO qnABoardVO = null;
        if(resultSet.next()) {
            qnABoardVO = QnABoardVO.builder()
                    .pno(pno)
                    .questionDate(resultSet.getString("questionDate"))
                    .answerDate(resultSet.getString("answerDate"))
                    .questionContent(resultSet.getString("questionContent"))
                    .qno(qno)
                    .answerContent(resultSet.getString("answerContent"))
                    .answered(resultSet.getBoolean("answered"))
                    .emailId(resultSet.getString("emailId"))
                    .build();
        }
        return qnABoardVO;

    }

    public List<QnABoardVO> selectQnAByPno(int pno, int currentPage, int limit) throws Exception {
        // pcode에 따른 qna 리스트
        String sql="select * from qna_board where pno=? order by qno limit ?,?";

        int beginRow=(currentPage-1)*limit;

        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setInt(1, pno);
        preparedStatement.setInt(2, beginRow);
        preparedStatement.setInt(3, limit);
        System.out.println("qnaBoardList 목록 출력 : "+preparedStatement);
        @Cleanup ResultSet resultSet=preparedStatement.executeQuery();

        ArrayList<QnABoardVO> qnaList=new ArrayList<>();
        while(resultSet.next()) {
            QnABoardVO qnABoardVO= QnABoardVO.builder()
                    .pno(resultSet.getInt("pno"))
                    .questionDate(resultSet.getString("questionDate"))
                    .answerDate(resultSet.getString("answerDate"))
                    .questionContent(resultSet.getString("questionContent"))
                    .qno(resultSet.getInt("qno"))
                    .answerContent(resultSet.getString("answerContent"))
                    .answered(resultSet.getBoolean("answered"))
                    .emailId(resultSet.getString("emailId"))
                    .secreted(resultSet.getBoolean("secreted"))
                    .build();
            qnaList.add(qnABoardVO);
        }

        return qnaList;
    }


    public void insertQnABoard(QnABoardVO qnaBoardVO) throws Exception {
        String sql="insert into qna_board (emailId, pno, productName, questionContent, secreted, questionDate) "+
                "values(?, ?, ?, ?, ?, now())";
        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);

        preparedStatement.setString(1, qnaBoardVO.getEmailId());
        preparedStatement.setInt(2, qnaBoardVO.getPno());
        preparedStatement.setString(3, qnaBoardVO.getProductName());
        preparedStatement.setString(4, qnaBoardVO.getQuestionContent());
        preparedStatement.setBoolean(5, qnaBoardVO.isSecreted());
        preparedStatement.executeUpdate();
    }

    public void updateQuestionBoard(QnABoardVO qnaBoardVO) throws Exception {
        String sql="update qna_board set questionContent=?, secreted = ? where qno=?";

        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);

        preparedStatement.setString(1, qnaBoardVO.getQuestionContent());
        preparedStatement.setBoolean(2, qnaBoardVO.isSecreted());
        preparedStatement.setInt(3, qnaBoardVO.getQno());

        preparedStatement.executeUpdate();
    }

    public void updateAnswerBoard(QnABoardVO qnaBoardVO) throws Exception {
        String sql="update qna_board set answerContent=?, answerDate=now(), answered=? where qno=?";

        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);

        preparedStatement.setString(1, qnaBoardVO.getAnswerContent());
        preparedStatement.setBoolean(2, true);
        preparedStatement.setInt(3, qnaBoardVO.getQno());

        preparedStatement.executeUpdate();
    }


    public void deleteQnABoard(int qno) throws Exception {
        String sql="delete from qna_board where qno=?";

        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);

        preparedStatement.setInt(1, qno);
        preparedStatement.executeUpdate();
    }
    public List<QnABoardVO> selectQnABoardByEmailId(String emailId, int currentPage, int limit) throws Exception {
        // 마이페이지
        String sql="select * from qna_board where emailId=? order by qno limit ?,?";

        int beginRow=(currentPage-1)*limit;

        @Cleanup Connection connection= ConnectionUtil.INSTANCE.getConnection();
        @Cleanup PreparedStatement preparedStatement=connection.prepareStatement(sql);
        preparedStatement.setString(1, emailId);
        preparedStatement.setInt(2, beginRow);
        preparedStatement.setInt(3, limit);
        @Cleanup ResultSet resultSet=preparedStatement.executeQuery();

        ArrayList<QnABoardVO> qnaList=new ArrayList<>();
        if(resultSet.next()) {
            QnABoardVO qnABoardVO = QnABoardVO.builder()
                    .pno(resultSet.getInt("pno"))
                    .questionDate(resultSet.getString("questionDate"))
                    .answerDate(resultSet.getString("answerDate"))
                    .questionContent(resultSet.getString("questionContent"))
                    .qno(resultSet.getInt("qno"))
                    .answerContent(resultSet.getString("answerContent"))
                    .answered(resultSet.getBoolean("answered"))
                    .emailId(emailId)
                    .secreted(resultSet.getBoolean("secreted"))
                    .productName(resultSet.getString("productName"))
                    .build();
            qnaList.add(qnABoardVO);
        }

        return qnaList;
    }


}
