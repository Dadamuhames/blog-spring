package com.example.demo.controllerAdvices;

import com.example.demo.models.Language;
import com.example.demo.services.FileGetService;
import com.example.demo.services.LanguageService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@ControllerAdvice
@RequiredArgsConstructor
public class TemplateContext {

    private final FileGetService fileGetService;
    private final LanguageService languageService;

    @ModelAttribute("request")
    public HttpServletRequest getRequest(HttpServletRequest request) {
        return request;
    }

    @ModelAttribute("requestUrl")
    public String getRequestUrl(HttpServletRequest request) {
        StringBuffer url = request.getRequestURL();

        return url.toString();
    }

    @ModelAttribute("page")
    public Integer getPage(HttpServletRequest request) {
        String page = request.getParameter("page");

        if(page == null) {
            page = "0";
        }

        return Integer.parseInt(page);
    }

    @ModelAttribute("size")
    public Integer getPageSize(HttpServletRequest request) {
        String pageSize = request.getParameter("pageSize");

        if(pageSize == null) {
            pageSize = "20";
        }

        return Integer.parseInt(pageSize);
    }

    @ModelAttribute("fileGetService")
    public FileGetService getFileGetService() {
        return fileGetService;
    }


    @ModelAttribute("defaultLang")
    public Language getDefaultLang() {
        return languageService.findDefault();
    }

    @ModelAttribute("langs")
    public List<Language> getLanguages() {
        return languageService.findActives();
    }

}
