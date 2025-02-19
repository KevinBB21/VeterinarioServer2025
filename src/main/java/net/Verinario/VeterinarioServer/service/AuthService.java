/*
 * Copyright (c) 2021
 *
 * by Rafael Angel Aznar Aparici (rafaaznar at gmail dot com) & 2021 DAW students
 *
 * WILDCART: Free Open Source Shopping Site
 *
 * Sources at:                https://github.com/rafaelaznar/wildCartSBServer2021
 * Database at:               https://github.com/rafaelaznar/wildCartSBServer2021
 * POSTMAN API at:            https://github.com/rafaelaznar/wildCartSBServer2021
 * Client at:                 https://github.com/rafaelaznar/wildCartAngularClient2021
 *
 * WILDCART is distributed under the MIT License (MIT)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package net.Verinario.VeterinarioServer.service;

import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import net.Verinario.VeterinarioServer.bean.UserBean;
import net.Verinario.VeterinarioServer.entity.UserEntity;
import net.Verinario.VeterinarioServer.exception.UnauthorizedException;
import net.Verinario.VeterinarioServer.helper.UsertypeHelper;
import net.Verinario.VeterinarioServer.repository.UserRepository;

@Service
public class AuthService {

    @Autowired
    HttpSession oHttpSession;

    @Autowired
    UserRepository oUserRepository;

    public UserEntity login(@RequestBody UserBean oUserBean) {
        if (oUserBean.getPassword() != null) {
            UserEntity oUserEntity = oUserRepository.findByUsernameAndPassword(oUserBean.getUsername(), oUserBean.getPassword());
            if (oUserEntity != null) {
                oHttpSession.setAttribute("user", oUserEntity);
                return oUserEntity;
            } else {
                throw new UnauthorizedException("login or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }

 
    public void logout() {
        oHttpSession.invalidate();
    }

    public UserEntity check() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        if (oUserSessionEntity != null) {
            return oUserSessionEntity;
        } else {
            throw new UnauthorizedException("no active session");
        }
    }

    public boolean isLoggedIn() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        if (oUserSessionEntity == null) {
            return false;
        } else {
            return true;
        }
    }

    public Long getUserID() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        if (oUserSessionEntity != null) {
            return oUserSessionEntity.getId();
        } else {
            throw new UnauthorizedException("this request is only allowed to auth users");
        }
    }

    public boolean isAdmin() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getUsertype().getId().equals(UsertypeHelper.ADMIN)) {
                return true;
            }
        }
        return false;
    }

    public boolean isUser() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getUsertype().getId().equals(UsertypeHelper.USER)) {
                return true;
            }
        }
        return false;
    }

    public void OnlyAdmins() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        System.out.println(oHttpSession.getAttribute("user"));
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to admin role");
        } else {
            if (!oUserSessionEntity.getUsertype().getId().equals(UsertypeHelper.ADMIN)) {
                throw new UnauthorizedException("this request is only allowed to admin role");
            }
        }
    }

    public void OnlyUsers() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user role");
        } else {
            if (!oUserSessionEntity.getUsertype().getId().equals(UsertypeHelper.USER)) {
                throw new UnauthorizedException("this request is only allowed to user role");
            }
        }
    }

    public void OnlyAdminsOrUsers() {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        if (oUserSessionEntity == null) {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        } else {

        }
    }

    public void OnlyAdminsOrOwnUsersData(Long id) {
        UserEntity oUserSessionEntity = (UserEntity) oHttpSession.getAttribute("user");
        if (oUserSessionEntity != null) {
            if (oUserSessionEntity.getUsertype().getId().equals(UsertypeHelper.USER)) {
                if (!oUserSessionEntity.getId().equals(id)) {
                    throw new UnauthorizedException("this request is only allowed for your own data");
                }
            }
        } else {
            throw new UnauthorizedException("this request is only allowed to user or admin role");
        }
    }
    /* 
    @Transactional
    public CaptchaResponse prelogin() {

        Long iRandom = Long.valueOf(RandomHelper.getRandomInt(1, (int) oQuestionRepository.count())); //pte cambiar el 5 

        QuestionEntity oQuestionEntity = oQuestionRepository.findById(iRandom).get();

        PendentEntity oPendentEntity = new PendentEntity();
        oPendentEntity.setQuestion(oQuestionEntity);
        PendentEntity oPendentEntityNueva = oPendentRepository.save(oPendentEntity);
        oPendentEntityNueva.setToken(RandomHelper.getSHA256("" + iRandom + oPendentEntityNueva.getId() + RandomHelper.getRandomInt(1, 9999)));
        oPendentRepository.save(oPendentEntityNueva);

        CaptchaResponse oCaptchaResponse = new CaptchaResponse();
        oCaptchaResponse.setQuestion(oPendentEntityNueva.getQuestion().getStatement());
        oCaptchaResponse.setToken(oPendentEntityNueva.getToken());

        return oCaptchaResponse;
    }

    public UserEntity loginc(CaptchaBean oCaptchaBean) {
        if (oCaptchaBean.getPassword() != null) {
            UserEntity oUserEntity = oUserRepository.findByLoginAndPassword(oCaptchaBean.getLogin(), oCaptchaBean.getPassword());
            if (oUserEntity != null) {
                // valida login y pass
                PendentEntity oPendentEntity = oPendentRepository.findByToken(oCaptchaBean.getToken());
                if (oPendentEntity.getQuestion().getResponse().toLowerCase().contains(oCaptchaBean.getAnswer().toLowerCase())) {
                    oHttpSession.setAttribute("User", oUserEntity);
                    //borrar el reg
                    oPendentRepository.delete(oPendentEntity);                                        
                    return oUserEntity;
                } else {
                    throw new UnauthorizedException("wrong login or password or response");
                }
            } else {
                throw new UnauthorizedException("login or password incorrect");
            }
        } else {
            throw new UnauthorizedException("wrong password");
        }
    }
*/
}
