package market.demo.service;

import lombok.RequiredArgsConstructor;
import market.demo.Util.Utils;
import market.demo.dto.FollowDTO;
import market.demo.entity.Follow;
import market.demo.repository.FollowRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final FollowRepository followRepository;
    public List<FollowDTO> getAll(){
        List<Follow> result = followRepository.findAll();
        return result.stream().map(Follow::toDTO).collect(Collectors.toList());
    }

    public FollowDTO create(Long uid, Long followingId) {
        Optional<Follow> optionalFollow = followRepository.findFollowByFollowerIdAndFollowingId(uid,followingId);
        if(optionalFollow.isPresent()){
            throw new IllegalArgumentException("Follow already exists");
        }
        Follow follow = new Follow();
        follow.setFollowerId(uid);
        follow.setFollowingId(followingId);
        follow.setCreateBy(uid);
        follow.setCreatedAt(LocalDateTime.now());
        follow.setStatus(Utils.Status.ACTIVE);
        follow = followRepository.save(follow);
        return Follow.toDTO(follow);
    }
    public FollowDTO delete(Long uid,Long followingId){
        Optional<Follow> optionalFollow = followRepository.findFollowByFollowerIdAndFollowingId(uid,followingId);
        if(optionalFollow.isEmpty()){
            throw new IllegalArgumentException("Follow not found");
        }
        followRepository.deleteByFollowerIdAndFollowingId(uid,followingId);
        return Follow.toDTO(optionalFollow.get());
    }

    public List<FollowDTO> getAllByFollowerId(Long id) {
        List<Follow> result = followRepository.getAllByFollowerId(id);
        if(result.isEmpty()){
            throw new IllegalArgumentException("Following not found");
        }
        return result.stream().map(Follow::toDTO).collect(Collectors.toList());
    }
}
