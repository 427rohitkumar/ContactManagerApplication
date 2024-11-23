package com.spring.smart.contactmanager.contactmanager.helper;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import jakarta.servlet.http.HttpSession;

@Component
public class SessionHelper {
    
    public void removeMessageFromSession(){
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes(); 
            if (attributes != null) { 
                HttpSession session = attributes.getRequest().getSession(false); 
                // use false to avoid creating a new session 
                if (session != null) { 
                    session.removeAttribute("message"); 
                  } 
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
