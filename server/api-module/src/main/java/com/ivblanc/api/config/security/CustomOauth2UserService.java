package com.ivblanc.api.config.security;

import java.util.Collections;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.ivblanc.api.config.security.dto.JwtUserDetails;
import com.ivblanc.api.oauth.info.OAuth2UserInfo;
import com.ivblanc.api.oauth.exception.OAuthProviderMissMatchException;
import com.ivblanc.api.oauth.info.OAuth2UserInfoFactory;
import com.ivblanc.core.code.ProviderType;
import com.ivblanc.core.entity.User;
import com.ivblanc.core.repository.UserRepository;
import com.ivblanc.core.utils.JwtTokenUtil;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CustomOauth2UserService extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	private final JwtTokenUtil jwtTokenUtil;
	private final JwtTokenProvider jwtTokenProvider;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		System.out.println("attributes" + super.loadUser(userRequest).getAttributes());
		OAuth2User user = super.loadUser(userRequest);
		//여기서 attriutes를 찍어보면 모두 각기 다른 이름으로 데이터가 들어오는 것을 확인할 수 있다.
		try{
			return process(userRequest, user);
		} catch (AuthenticationException ex){
			ex.printStackTrace();
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		} catch (Exception ex){
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	//인증을 요청하는 사용자에 따라서 없는 회원이면 회원가입, 이미 존재하는 회원이면 업데이트를 실행한다.
	private OAuth2User process(OAuth2UserRequest userRequest, OAuth2User user) {
		ProviderType providerType = ProviderType.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());
		//provider타입에 따라서 각각 다르게 userInfo가져온다. (가져온 필요한 정보는 OAuth2UserInfo로 동일하다)
		OAuth2UserInfo userInfo = OAuth2UserInfoFactory.getOAuth2UserInfo(providerType, user.getAttributes());

		User savedUser = userRepository.findByEmail(userInfo.getEmail());

		if (savedUser != null) {
			if (providerType != savedUser.getProvider()) {
				throw new OAuthProviderMissMatchException(
					"Looks like you're signed up with " + providerType +
						" account. Please use your " + savedUser.getProvider() + " account to login."
				);
			}
			updateUser(savedUser, userInfo);
		} else {
			savedUser = createUser(userInfo, providerType);
		}
		System.out.println(jwtTokenUtil.generateToken(savedUser));

		return new JwtUserDetails(savedUser, user.getAttributes());
	}

	//넘어온 사용자 정보를 통해서 회원가입을 실행한다.
	private User createUser(OAuth2UserInfo userInfo, ProviderType providerType) {
		User user = new User();
		user = User.builder()
			.social(1)
			.roles(Collections.singletonList("ROLE_USER"))
			.password("")
			.email(userInfo.getEmail())
			.name(userInfo.getName())
			.phone(userInfo.getPhone())
			.age(userInfo.getAge())
			.gender(userInfo.getGender())
			.provider(providerType)
			.build();


		return userRepository.save(user);
	}

	//사용자정보에 변경이 있다면 사용자 정보를 업데이트 해준다.
	private User updateUser(User user, OAuth2UserInfo userInfo) {

		// if (userInfo.getImageUrl() != null && !user.getImageUrl().equals(userInfo.getImageUrl())) {
		// 	user.setImageUrl(userInfo.getImageUrl());
		// }

		return user;
	}
}