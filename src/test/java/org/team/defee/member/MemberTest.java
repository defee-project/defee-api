package org.team.defee.member;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.team.defee.common.util.HashUtil;
import org.team.defee.member.dto.RegisterDto;
import org.team.defee.member.entity.Member;
import org.team.defee.member.repository.MemberRepository;
import org.team.defee.member.service.MemberService;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MemberTest {
    @InjectMocks
    private MemberService sut;
    @Mock
    private MemberRepository repository;
    @Spy
    private HashUtil hashUtil = new HashUtil(new BCryptPasswordEncoder()); // 실제 객체 사용


    @Test
    @DisplayName("register 테스트")
    public void testRegister() {
        // given
        RegisterDto registerDto = new RegisterDto();
        registerDto.setEmail("test@test.com");
        registerDto.setPassword("123456");
        registerDto.setUsername("test");
        registerDto.setBlogUrl("http://testblog.com");


        // Mock 동작 정의
        doAnswer(invocation -> {
            Member member = invocation.getArgument(0);
            member.setId(1L); // save 호출 시 ID 설정
            return null;
        }).when(repository).save(any(Member.class));

        // when
        Long id = sut.register(registerDto);

        // then
        assertEquals(1L, id);
        verify(repository, times(1)).save(any(Member.class)); // save 호출 확인
    }

    @Test
    @DisplayName("validateEmail 테스트 - 예외 상황")
    public void testValidateEmailThrowsException() {
        // given
        String email = "test@test.com";
        Member member = new Member();
        member.setEmail(email);
        List<Member> existingMembers = List.of(member); // 이미 존재하는 사용자 설정

        when(repository.findByEmail(email)).thenReturn(existingMembers);

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            sut.validateEmail(email);
        });
        assertEquals("이미 사용중인 이메일입니다.", exception.getMessage());

    }

    @Test
    @DisplayName("validateEmail 테스트 - 정상 상황")
    public void testValidateEmailPasses() {
        // given
        String email = "test@test.com";
        when(repository.findByEmail(email)).thenReturn(Collections.emptyList());

        // when
        sut.validateEmail(email);

        // then
        verify(repository, times(1)).findByEmail(email);
    }


    @Test
    @DisplayName("validateUsername 테스트 - 예외 상황")
    public void testValidateUsernameThrowsException() {
        // given
        String username = "test";
        Member member = new Member();
        member.setUsername(username);
        List<Member> existingMembers = List.of(member);
        when(repository.findByUsername(username)).thenReturn(existingMembers);

        // when & then
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> {
            sut.validateUsername(username);
        });
        assertEquals("이미 사용중인 아이디입니다.", exception.getMessage());
    }

    @Test
    @DisplayName("validateUsername 테스트 - 정상 상황")
    public void testValidateUsernamePasses() {
        // given
        String username = "test";
        when(repository.findByUsername(username)).thenReturn(Collections.emptyList());
        // when
        sut.validateUsername(username);
        // then
        verify(repository, times(1)).findByUsername(username);
    }

    @Test
    @DisplayName("findMembers 테스트")
    public void testFindMembers() {
        // given
        Member member1 = new Member();
        member1.setId(1L);
        member1.setEmail("test1@test.com");
        member1.setUsername("test1");

        Member member2 = new Member();
        member2.setId(2L);
        member2.setEmail("test2@test.com");
        member2.setUsername("test2");

        List<Member> mockMembers = Arrays.asList(member1, member2);
        when(repository.findAll()).thenReturn(mockMembers);

        // when
        List<Member> members = sut.findMembers();

        // then
        assertEquals(2, mockMembers.size());
        assertEquals(member1, members.get(0)); // 첫 번째 요소 비교
        assertEquals(member2, members.get(1)); // 두 번째 요소 비교
        verify(repository, times(1)).findAll();
    }

    @Test
    @DisplayName("findOne 테스트")
    public void testFindOne() {
        // given
        Long id = 1L;
        Member member = new Member();
        member.setId(id);
        member.setEmail("test1@test.com");
        member.setUsername("test1");

        when(repository.findOne(id)).thenReturn(member);

        // when
        Member result = sut.findOne(id);

        // then
        assertEquals(member, result);
        assertEquals("test1@test.com", result.getEmail());
        assertEquals(id, result.getId());
        verify(repository, times(1)).findOne(id);
    }

    @Test
    @DisplayName("findOneByEmail 테스트")
    public void testFindOneByEmail() {
        // given
        String email = "test1@test.com";
        Member member1 = new Member();
        member1.setEmail(email);
        member1.setUsername("test1");

        Member member2 = new Member();
        member2.setEmail("test2@test.com");
        member2.setUsername("test2");

        List<Member> mockMembers = Arrays.asList(member1, member2);
        when(repository.findByEmail(email)).thenReturn(mockMembers);

        // when
        Member result = sut.findOneByEmail(email);

        // then
        assertEquals(member1, result);
        assertEquals(email, result.getEmail());
        verify(repository, times(1)).findByEmail(email);
    }
}
