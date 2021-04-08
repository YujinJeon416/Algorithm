package member.model.dao;

import static common.JDBCTemplate.close;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import member.model.vo.Member;

public class MemberDao {

	private Properties prop = new Properties();

	public MemberDao() {
		String fileName = MemberDao.class.getResource("/sql/member/member-query.properties").getPath();
		try {
			prop.load(new FileReader(fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public Member selectOne(Connection conn, String memberId) {
		Member member = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("selectOne");

		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 미완성 쿼리문 값대입
			pstmt.setString(1, memberId);
			// 쿼리문실행
			rset = pstmt.executeQuery();

			if (rset.next()) {
				member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setGender(rset.getString("GENDER"));
				member.setBirthday(rset.getDate("BIRTHDAY"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return member;

	}

	public int insertMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("insertMember");

		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문미완성
			pstmt.setString(1, member.getMemberId());
			pstmt.setString(2, member.getPassword());
			pstmt.setString(3, member.getMemberName());
			pstmt.setString(4, member.getMemberRole());
			pstmt.setString(5, member.getGender());
			pstmt.setDate(6, member.getBirthday());
			pstmt.setString(7, member.getEmail());
			pstmt.setString(8, member.getPhone());
			pstmt.setString(9, member.getAddress());
			pstmt.setString(10, member.getHobby());

			// 쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			// DML은 executeUpdate()
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int updateMember(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateMember");

		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문미완성
//			pstmt.setString(1, member.getPassword());
//			pstmt.setString(2, member.getMemberName());
//			pstmt.setString(3, member.getGender());
//			pstmt.setDate(4, member.getBirthday());
//			pstmt.setString(5, member.getEmail());
//			pstmt.setString(6, member.getPhone());
//			pstmt.setString(7, member.getAddress());
//			pstmt.setString(8, member.getHobby());
//			pstmt.setString(9, member.getMemberId());

			pstmt.setString(1, member.getMemberName());
			pstmt.setString(2, member.getGender());
			pstmt.setDate(3, member.getBirthday());
			pstmt.setString(4, member.getEmail());
			pstmt.setString(5, member.getPhone());
			pstmt.setString(6, member.getAddress());
			pstmt.setString(7, member.getHobby());
			pstmt.setString(8, member.getMemberId());

			// 쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			// DML은 executeUpdate()
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public int deleteMember(Connection conn, String membmerId) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("deleteMember");

		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문미완성
			pstmt.setString(1, membmerId);

			// 쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			// DML은 executeUpdate()
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}

	public int updatePassword(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updatePassword");
		// updatePassword = update member set password = ? where member_id = ?
		
		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문미완성
			pstmt.setString(1, member.getPassword());
			pstmt.setString(2, member.getMemberId());

			// 쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			// DML은 executeUpdate()
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public List<Member> selectList(Connection conn) {
		List<Member> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("selectList");
		//select * from member order by enroll_date desc
		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문실행
			rset = pstmt.executeQuery();

			list = new ArrayList<>();
			while (rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setGender(rset.getString("GENDER"));
				member.setBirthday(rset.getDate("BIRTHDAY"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}
	
	public List<Member> selectList(Connection conn, int start, int end) {
		List<Member> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		String query = prop.getProperty("selectPagedList");
		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			// 쿼리문실행
			rset = pstmt.executeQuery();

			list = new ArrayList<>();
			while (rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setGender(rset.getString("GENDER"));
				member.setBirthday(rset.getDate("BIRTHDAY"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int updateMemberRole(Connection conn, Member member) {
		int result = 0;
		PreparedStatement pstmt = null;
		String query = prop.getProperty("updateMemberRole");
		
		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문미완성
			pstmt.setString(1, member.getMemberRole());
			pstmt.setString(2, member.getMemberId());

			// 쿼리문실행 : 완성된 쿼리를 가지고 있는 pstmt실행(파라미터 없음)
			// DML은 executeUpdate()
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			close(pstmt);
		}

		return result;
	}

	public List<Member> searchMember(Connection conn, Map<String, String> param) {
		List<Member> list = null;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("searchPagedMember");
		//select * from member where member_id like %a%
		//select * from member where member_name like %동%
		//select * from member where gender = 'M'
		//select * from ( select row_number() over(order by enroll_date desc) rnum, M.* from member M where #) M where rnum between ? and ?
		query = setQuery(query, param.get("searchType"), param.get("searchKeyword"));
		System.out.println("query@dao = " + query);
		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, param.get("start"));
			pstmt.setString(2, param.get("end"));
			// 쿼리문실행
			rset = pstmt.executeQuery();

			list = new ArrayList<>();
			while (rset.next()) {
				Member member = new Member();
				member.setMemberId(rset.getString("MEMBER_ID"));
				member.setPassword(rset.getString("PASSWORD"));
				member.setMemberName(rset.getString("MEMBER_NAME"));
				member.setMemberRole(rset.getString("MEMBER_ROLE"));
				member.setGender(rset.getString("GENDER"));
				member.setBirthday(rset.getDate("BIRTHDAY"));
				member.setEmail(rset.getString("EMAIL"));
				member.setPhone(rset.getString("PHONE"));
				member.setAddress(rset.getString("ADDRESS"));
				member.setHobby(rset.getString("HOBBY"));
				member.setEnrollDate(rset.getDate("ENROLL_DATE"));
				
				list.add(member);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return list;
	}

	public int selectMemberCount(Connection conn) {
		int totalContents = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("selectMemberCount");

		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문실행
			rset = pstmt.executeQuery();

			if (rset.next()) {
				totalContents = rset.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return totalContents;
	}

	public int searchMemberCount(Connection conn, Map<String, String> param) {
		int totalContents = 0;
		PreparedStatement pstmt = null;
		ResultSet rset = null;

		String query = prop.getProperty("searchMemberCount");
		//select count(*) cnt from member M where #
		query = setQuery(query, param.get("searchType"), param.get("searchKeyword"));
		System.out.println("query@dao = " + query);

		try {
			// 미완성쿼리문을 가지고 객체생성.
			pstmt = conn.prepareStatement(query);
			// 쿼리문실행
			rset = pstmt.executeQuery();

			if (rset.next()) {
				totalContents = rset.getInt("cnt");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			close(rset);
			close(pstmt);
		}
		return totalContents;
	}

	public String setQuery(String query, String searchType, String searchKeyword) {
		switch(searchType) {
		case "memberId" 	: query = query.replace("#", " member_id like '%" + searchKeyword + "%'"); break;
		case "memberName" 	: query = query.replace("#", " member_name like '%" + searchKeyword + "%'"); break;
		case "gender" 		: query = query.replace("#", " gender = '" + searchKeyword + "'"); break;
		}
		
		return query;
	}
	

}
