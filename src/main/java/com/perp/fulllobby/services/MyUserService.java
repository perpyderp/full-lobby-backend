package com.perp.fulllobby.services;


import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.http.HttpResponse;
import com.perp.fulllobby.exception.CannotFindFriendRequestException;
import com.perp.fulllobby.exception.CannotFriendSelf;
import com.perp.fulllobby.exception.CannotUpdateUserException;
import com.perp.fulllobby.exception.EmailAlreadyTakenException;
import com.perp.fulllobby.exception.UnableToSaveAvatarException;
import com.perp.fulllobby.exception.UnableToSaveBannerException;
import com.perp.fulllobby.exception.UnableToSendFriendRequest;
import com.perp.fulllobby.exception.UserNotFoundException;
import com.perp.fulllobby.model.Friendship;
import com.perp.fulllobby.model.Image;
import com.perp.fulllobby.model.MyUser;
import com.perp.fulllobby.model.RegistrationObject;
import com.perp.fulllobby.model.Role;
import com.perp.fulllobby.repository.FriendRepository;
import com.perp.fulllobby.repository.RoleRepository;
import com.perp.fulllobby.repository.UserRepository;

@Service
public class MyUserService implements UserDetailsService{
    
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final FriendRepository friendRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageService imageService;

    public MyUserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, 
                        ImageService imageService, FriendRepository friendRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.friendRepository = friendRepository;
        this.passwordEncoder = passwordEncoder;
        this.imageService = imageService;
    }

    public MyUser getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public List<MyUser> getAllUsers() {
        return userRepository.findAll();
    }

    public MyUser getByUsername(String username) {
        MyUser user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        return user;
    }

    public MyUser registerUser(RegistrationObject newUser) {
        MyUser user = new MyUser();
        System.out.println(newUser);
        user.setUsername(newUser.getUsername());
        user.setPassword(passwordEncoder.encode(newUser.getPassword()));
        user.setEmail(newUser.getEmail());

        Set<Role> roles = user.getAuthorities();
        roles.add(roleRepository.findByAuthority("USER").get());
        user.setAuthorities(roles);

        try {
            return userRepository.save(user);
        }
        catch (Exception e) {
            throw new EmailAlreadyTakenException();
        }
    }

    public MyUser updateUser(MyUser updatedUser) {
        try {
            return userRepository.save(updatedUser);
        }
        catch (Exception e) {
            throw new CannotUpdateUserException();
        }
    }

    public Set<MyUser> getUserFriends(String username) {
        
        MyUser user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        return user.getFriends();

    }

    public ResponseEntity<HttpResponse> removeFriend(MyUser removeFriend) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        MyUser user = userRepository.findByUsername(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Set<GrantedAuthority> authorities = user.getAuthorities()
            .stream()
            .map(role -> new SimpleGrantedAuthority(role.getAuthority()))
            .collect(Collectors.toSet());

        UserDetails userDetails = new User(user.getUsername(), user.getPassword(), authorities);
        
        return userDetails;

    }

    public MyUser setBanner(String username, MultipartFile file) throws UnableToSaveBannerException{
        MyUser user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        Image avatar = imageService.uploadBanner(file);

        user.setBanner(avatar);

        return userRepository.save(user);
    }

    public MyUser setAvatar(String username, MultipartFile file) throws UnableToSaveAvatarException{
        MyUser user = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);

        Image banner = imageService.uploadAvatar(file);

        user.setBanner(banner);

        return userRepository.save(user);
    }

    public ResponseEntity<String> sendFriendRequest(String username, String friendName) throws UnableToSendFriendRequest, CannotFriendSelf{

        if(username.equals(friendName)) throw new CannotFriendSelf();

        MyUser currentUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        MyUser addedUser = userRepository.findByUsername(friendName).orElseThrow(UserNotFoundException::new);

        Friendship newFriend = new Friendship();
        newFriend.setFirstUser(currentUser);
        newFriend.setSecondUser(addedUser);

        try {
            friendRepository.save(newFriend);
        }
        catch (Exception e) {
            throw new UnableToSendFriendRequest();
        }
        
        return new ResponseEntity<String>("Successfully sent friend request", HttpStatus.CREATED);
    }

    public Set<MyUser> acceptFriend(String username, String friendName) throws CannotFindFriendRequestException{

        MyUser currentUser = userRepository.findByUsername(username).orElseThrow(UserNotFoundException::new);
        MyUser friend = userRepository.findByUsername(friendName).orElseThrow(UserNotFoundException::new);

        Friendship friendship = friendRepository.findByFirstUserAndSecondUser(currentUser, friend).orElseThrow(CannotFindFriendRequestException::new);

        friendship.setAccepted(true);

        Set<MyUser> currentUserFriendList = currentUser.getFriends();

        Set<MyUser> friendList = friend.getFriends();

        currentUserFriendList.add(friend);
        currentUser.setFriends(currentUserFriendList);
        
        friendList.add(currentUser);
        friend.setFriends(friendList);

        userRepository.save(currentUser);
        userRepository.save(friend);

        friendRepository.save(friendship);

        return currentUser.getFriends();

    }

    public MyUser verifyUser(String username, Long code) {
        return null;
    }

}
