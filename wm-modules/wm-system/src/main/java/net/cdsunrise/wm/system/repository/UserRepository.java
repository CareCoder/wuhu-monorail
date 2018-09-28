package net.cdsunrise.wm.system.repository;

import net.cdsunrise.wm.system.entity.Position;
import net.cdsunrise.wm.system.entity.Role;
import net.cdsunrise.wm.system.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Author : WangRui
 * Date : 2018/4/13
 * Describe :
 */
public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsername(String username);

    List<User> findByIdIn(List<Long> ids);

    List<User> findByRole(Role role);

    List<User> findByPosition(Position position);
}
