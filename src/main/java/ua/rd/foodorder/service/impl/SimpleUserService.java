package ua.rd.foodorder.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ua.rd.foodorder.domain.User;
import ua.rd.foodorder.infrastructure.exceptions.EntityNotFoundException;
import ua.rd.foodorder.repository.UserRepository;
import ua.rd.foodorder.service.UserService;

@Service
@Transactional
public class SimpleUserService implements UserService {

	private UserRepository userRepository;

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public User findById(Long id) {
		return userRepository.findOne(id);
	}

	@Override
	public User update(User user) {
		User userInDB = userRepository.findOne(user.getId());

		if (userInDB == null) {
			throw new EntityNotFoundException(user.getId());
		}

		userInDB.setActive(user.isActive());
		userInDB.setAdmin(user.isAdmin());
		userInDB.setEmail(user.getEmail());
		userInDB.setName(user.getName());

		return userRepository.save(userInDB);
	}

	@Override
	public void remove(Long id) {
		User userInDB = userRepository.findOne(id);

		if (userInDB == null) {
			throw new EntityNotFoundException(id);
		}

		userInDB.setActive(false);

		userRepository.save(userInDB);
	}

	@Override
	public User save(User user) {
		return userRepository.save(user);
	}

	@Override
	public Page<User> getPageOfUsers(PageRequest pageRequest) {
		return userRepository.findAll(pageRequest);
	}

	@Autowired
	public void setUserRepository(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

}
