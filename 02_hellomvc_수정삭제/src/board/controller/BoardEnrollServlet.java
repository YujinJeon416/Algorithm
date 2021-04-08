package board.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import board.model.service.BoardService;
import board.model.vo.Attachment;
import board.model.vo.Board;
import common.MvcFileRenamePolicy;

/**
 * Servlet implementation class BoardEnrollServlet
 */
@WebServlet("/board/boardEnroll")
public class BoardEnrollServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private BoardService boardService = new BoardService();
	
	/**
	 * 0. form의 속성 enctype="multipart/form-data" 추가
	 * 1. MultipartRequest객체 생성 : 서버컴퓨터 파일 저장 
	 * 		- request
	 * 		- 저장경로
	 * 		- encoding
	 * 		- 최대허용크기
	 * 		- 파일명 변경정책 객체
	 * 2. db에 파일정보를 저장 
	 * 		- 사용자가 저장한 파일명 original_filename
	 * 		- 실제 저장된 파일명 renamed_filename
	 * 
	 * MultipartRequest객체를 사용하면, 
	 * 기존 HttpServletRequest에서는 사용자입력값에 접근할 수 없다.
	 * 
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			//1. MultipartRequest객체 생성
			// /WebContent/upload/board/업로드파일명.jpg 
			// web rool dir를 절대경로로 반환
			String saveDirectory = getServletContext().getRealPath("/upload/board");
			System.out.println("saveDirectory@servlet = " + saveDirectory);
			
			//최대파일허용크기 10mb = 10 * 1kb * 1kb
			int maxPostSize = 10 * 1024 * 1024;
			
			//인코딩
			String encoding = "utf-8";
			
			//파일명 변경정책 객체
			//중복파일인 경우, numbering처리
			//filerename : 20210406191919_123.jpg
	//		FileRenamePolicy policy = new DefaultFileRenamePolicy();
			FileRenamePolicy policy = new MvcFileRenamePolicy();
			
			MultipartRequest multipartRequest = 
					new MultipartRequest(
									request, 
									saveDirectory, 
									maxPostSize, 
									encoding, 
									policy
								);
			
			//2. db에 게시글/첨부파일 정보 저장
			
			//2-1. 사용자 입력값처리
			String title = multipartRequest.getParameter("title");
			String  writer = multipartRequest.getParameter("writer");
			String content = multipartRequest.getParameter("content");
			
			//업로드한 파일명
			String originalFileName = multipartRequest.getOriginalFileName("upFile");
			String renamedFileName = multipartRequest.getFilesystemName("upFile");
			
	//		Board board = new Board(0, title, writer, content, null, 0, null);
			Board board = new Board();
			board.setTitle(title);
			board.setWriter(writer);
			board.setContent(content);
			
			//첨부파일이 있는 경우
			//multipartRequest.getFile("upFile"):File != null
			if(originalFileName != null) {
				Attachment attach = new Attachment();
				attach.setOriginalFileName(originalFileName);
				attach.setRenamedFileName(renamedFileName);
				board.setAttach(attach);
			}
			
			//2. 업무로직 : db에 insert
			int result = boardService.insertBoard(board);
			String msg = (result > 0) ? 
							"게시글 등록 성공!" :
								"게시글 등록 실패!";
			String location = request.getContextPath();
			location += (result > 0) ?
							"/board/boardView?no=" + board.getNo() : 
								"/board/boardList";
			
			//3. DML요청 : 리다이렉트 & 사용자피드백
			// /mvc/board/boardList
			HttpSession session = request.getSession();
			session.setAttribute("msg", msg);
			response.sendRedirect(location);
		} catch (Exception e) {
			e.printStackTrace();
			throw e; // was한테 다시 던져서 에러페이지로 전환함.
		}
	}

}
