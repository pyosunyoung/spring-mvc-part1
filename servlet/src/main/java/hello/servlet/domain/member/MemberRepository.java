package hello.servlet.domain.member;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 동시성 문제가 고려되어 있지 않음, 실무에서는 ConcurrentHashMap, AtomicLong 사용 고려
 */
public class MemberRepository {

    private static Map<Long,Member> store = new HashMap<>();
    private static long sequence = 0L; //ID?

    /// 싱글톤 패턴
    private static final MemberRepository instance = new MemberRepository(); // 싱글톤 만들기?, “클래스가 시작할 때 하나 만든다”, 변경하지마라:final

    public static MemberRepository getInstance() { // 이걸로만 접근해라
        return instance;
    }

    private MemberRepository() { //private 생성자 — “new 하지 마라”

    }
    /// 여기까지 싱글톤

    public Member save(Member member) {
        member.setId(++sequence);
        store.put(member.getId(), member);//저장하기
        return member;
    }
    public Member findById(Long id) {
        return store.get(id);
    }

    public List<Member> findAll() {
        return new ArrayList<Member>(store.values()); // store의 리스트를 건들지않고 새로운 arrayList에 저장한다.
    }

    public void clearStore() {
        store.clear();
    }
}
