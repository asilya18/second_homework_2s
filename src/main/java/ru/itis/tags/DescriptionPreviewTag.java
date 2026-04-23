package ru.itis.tags;

import jakarta.servlet.jsp.tagext.TagSupport;
import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.JspWriter;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import ru.itis.formatter.DescriptionPreviewFormatter;

import java.util.Locale;

public class DescriptionPreviewTag extends TagSupport {

    private String text;

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            WebApplicationContext ctx =
                    WebApplicationContextUtils.getRequiredWebApplicationContext(
                            pageContext.getServletContext()
                    );

            DescriptionPreviewFormatter formatter =
                    ctx.getBean(DescriptionPreviewFormatter.class);

            String result = formatter.print(text, Locale.getDefault());

            JspWriter out = pageContext.getOut();
            out.write(result);
            System.out.println("Formatter called, length=" + text.length());

        } catch (Exception e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }
}
