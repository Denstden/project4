package tags;

import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.DynamicAttributes;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.apache.log4j.Logger.getLogger;

public class CommandTag extends BodyTagSupport implements DynamicAttributes {
    public static final Logger logger = getLogger(CommandTag.class);
    private Map<String, Object> attributes = new HashMap<>();
    private String command;

    public void setCommand(String command) {
        this.command = command;
    }

    @Override
    public int doAfterBody() throws JspException {
        StringBuilder sb;
        try {
            sb = new StringBuilder();
            sb.append("<input type='hidden' name='command' value='").append(command).append("'/>");
            for (String s : attributes.keySet()) {
                sb.append("<input type='hidden' name='").append(s).append("' value='").append(attributes.get(s)).append("'/>");
            }
            sb.append("<input type='submit' class='button' value='").append(this.getBodyContent().getString()).append("'>");
            getBodyContent().getEnclosingWriter().print(sb.toString());

        } catch (IOException e) {
           logger.error(Arrays.toString(e.getStackTrace()));
        }
        return SKIP_BODY;
    }

    @Override
    public void setDynamicAttribute(String uri, String name, Object value) throws JspException {
        attributes.put(name, value);
    }
}

