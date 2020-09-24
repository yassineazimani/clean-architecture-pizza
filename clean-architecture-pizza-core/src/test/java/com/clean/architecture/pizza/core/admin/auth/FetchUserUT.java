package com.clean.architecture.pizza.core.admin.auth;

import com.clean.architecture.pizza.core.model.UserDTO;
import com.clean.architecture.pizza.core.ports.PasswordEncoder;
import com.clean.architecture.pizza.core.ports.UserRepository;
import com.clean.architecture.pizza.core.stub.UsersStub;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class FetchUserUT {

    private FetchUser fetchUser;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Before
    public void setUp(){
        this.fetchUser = new FetchUser(passwordEncoder, userRepository);
    }// setUp()

    @Test
    public void fetch_user_by_id_should_success_when_id_exists(){
        Mockito.when(this.userRepository.findById(1584690))
                .thenReturn(Optional.of(UsersStub.getUserAdmin()));
        Optional<UserDTO> optUser = this.fetchUser.findById(1584690);
        Assertions.assertThat(optUser).isPresent();
        optUser.ifPresent(user -> {
           Assertions.assertThat(user.getId()).isEqualTo(1584690);
           Assertions.assertThat(user.getPassword()).isNotEmpty();
        });
    }// fetch_user_by_id_should_success_when_id_exists()

    @Test
    public void fetch_user_by_id_should_success_when_id_doesnt_exists(){
        Optional<UserDTO> optUser = this.fetchUser.findById(1584691);
        Assertions.assertThat(optUser).isNotPresent();
    }// fetch_user_by_id_should_success_when_id_doesnt_exists()

    @Test
    public void fetch_user_by_id_and_password_should_success_when_id_exists(){
        Mockito.when(this.passwordEncoder.isEquals("password", "$2a$12$boWhkrsHVdNZhQIgQCq/sum1IV.Wp1SIb9rJMxsAue6TkT5Fge9je"))
                .thenReturn(true);
        Mockito.when(this.userRepository.findById(1584690))
                .thenReturn(Optional.of(UsersStub.getUserAdmin()));
        Optional<UserDTO> optUser = this.fetchUser.findByIdAndPassword(1584690, "password");
        Assertions.assertThat(optUser).isPresent();
        optUser.ifPresent(user -> {
            Assertions.assertThat(user.getId()).isEqualTo(1584690);
            Assertions.assertThat(user.getPassword()).isNotEmpty();
        });
    }// fetch_user_by_id_and_password_should_success_when_id_exists()

    @Test
    public void fetch_user_by_id_and_password_should_success_when_id_doesnt_exists(){
        Mockito.when(this.userRepository.findById(1584691))
                .thenReturn(Optional.empty());
        Optional<UserDTO> optUser = this.fetchUser.findByIdAndPassword(1584691, "password");
        Assertions.assertThat(optUser).isNotPresent();
    }// fetch_user_by_id_and_password_should_success_when_id_doesnt_exists()

    @Test
    public void fetch_user_by_id_and_password_should_success_when_password_is_empty_or_null(){
        Optional<UserDTO> optUser = this.fetchUser.findByIdAndPassword(1584691, "");
        Assertions.assertThat(optUser).isNotPresent();
        optUser = this.fetchUser.findByIdAndPassword(1584691, null);
        Assertions.assertThat(optUser).isNotPresent();
    }// fetch_user_by_id_and_password_should_success_when_password_is_empty_or_null()

    @Test
    public void fetch_user_by_id_and_password_should_success_when_password_is_wrong(){
        Mockito.when(this.passwordEncoder.isEquals("Password", "$2a$12$boWhkrsHVdNZhQIgQCq/sum1IV.Wp1SIb9rJMxsAue6TkT5Fge9je"))
                .thenReturn(false);
        Mockito.when(this.userRepository.findById(1584691))
                .thenReturn(Optional.of(UsersStub.getUserAdmin()));
        Optional<UserDTO> optUser = this.fetchUser.findByIdAndPassword(1584691, "Password");
        Assertions.assertThat(optUser).isNotPresent();
    }// fetch_user_by_id_and_password_should_success_when_password_is_wrong()

}// FetchUserUT
