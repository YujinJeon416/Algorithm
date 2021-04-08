package board.model.service;

import static common.JDBCTemplate.close;
import static common.JDBCTemplate.commit;
import static common.JDBCTemplate.getConnection;
import static common.JDBCTemplate.rollback;

import java.sql.Connection;
import java.util.List;

import board.model.dao.BoardDao;
import board.model.vo.Attachment;
import board.model.vo.Board;

public class BoardService {

	private BoardDao boardDao = new BoardDao();

	public List<Board> selectList(int start, int end) {
		Connection conn = getConnection();
		List<Board> list = boardDao.selectList(conn, start, end);
		close(conn);
		return list;
	}

	public int selectBoardCount() {
		Connection conn = getConnection();
		int totalContents = boardDao.selectBoardCount(conn);
		close(conn);
		return totalContents;
	}
	
	/**
	 * 첨부파일 있는 경우, attach객체를 attachment테이블에 등록한다. 
	 * - board등록, attachment등록은 하나의 트랜잭션으로 처리되어야한다.
	 * - 하나의 Connection에 의해 처리되어야한다.
	 * 
	 * @param board
	 * @return
	 */
	public int insertBoard(Board board) {
		Connection conn = getConnection();
		int result = 0;
		
		try {
			result = boardDao.insertBoard(conn, board);
			
			//생성된 board_no를 가져오기
			int boardNo = boardDao.selectLastBoardNo(conn);
			//redirect location설정
			board.setNo(boardNo);
			
//			System.out.println("boardNo@service = " + boardNo);
			
			if(board.getAttach() != null) {
				//참조할 boardNo세팅
				board.getAttach().setBoardNo(boardNo);
				result = boardDao.insertAttachment(conn, board.getAttach());
			}
			commit(conn);
			
		} catch(Exception e) {
			rollback(conn);
			throw e;
		} finally {			
			close(conn);
		}
		return result;
	}
	
	public Board selectOne(int no) {
		Connection conn = getConnection();
		Board board = boardDao.selectOne(conn, no);
		Attachment attach = boardDao.selectOneAttachment(conn, no);
		board.setAttach(attach);
		close(conn);
		return board;
	}

	/**
	 * board_no로 attachment 행 조회
	 * 
	 * @param no
	 * @return
	 */
	public Attachment selectOneAttachment(int no) {
		Connection conn = getConnection();
		Attachment attach = boardDao.selectOneAttachment(conn, no);
		close(conn);
		return attach;
	}

	public int deleteBoard(int no) {
		Connection conn = getConnection();
		int result = 0;
		try {
			result = boardDao.deleteBoard(conn, no);
			if(result == 0)
				throw new IllegalArgumentException("해당 게시글이 존재하지 않습니다. : " + no );
			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e; //controller가 예외처리를 결정할 수 있도록 넘김.
		} finally {
			close(conn);
		}
		return result;
	}

	public int updateBoard(Board board) {
		Connection conn = getConnection(); 
		int result = 0;
		try {
			//1.board update
			result = boardDao.updateBoard(conn, board);
			//2.attachment insert
			if(board.getAttach() != null)
				result = boardDao.insertAttachment(conn, board.getAttach());

			commit(conn);
		} catch(Exception e) {
			rollback(conn);
			throw e;
		}
		return result;
	}

	
}
