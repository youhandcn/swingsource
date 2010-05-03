package layeredpanel.StackLayout.io;

import java.io.FileFilter;
import java.io.File;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

/**
 * @author Romain Guy
 */
public class UnixGlobFileFilter implements FileFilter {
    private Pattern pattern;

    public UnixGlobFileFilter(String filter) {
        pattern = Pattern.compile(globToRegex(filter));
    }

    public boolean accept(File file) {
        String path = file.getName();
        Matcher matcher = pattern.matcher(path);
        return matcher.matches();
    }

    private String globToRegex(String glob) {
        char c = '\0';
        boolean escape = false;
        boolean enclosed = false;
        StringBuffer buffer = new StringBuffer(glob.length());

        for (int i = 0; i < glob.length(); i++) {
            c = glob.charAt(i);

            if (escape) {
                buffer.append('\\');
                buffer.append(c);
                escape = false;
                continue;
            }

            switch (c) {
                case '*':
                    buffer.append('.').append('*');
                    break;
                case '?':
                    buffer.append('.');
                    break;
                case '\\':
                    escape = true;
                    break;
                case '.':
                    buffer.append('\\').append('.');
                    break;
                case '{':
                    buffer.append('(');
                    enclosed = true;
                    break;
                case '}':
                    buffer.append(')');
                    enclosed = false;
                    break;
                case ',':
                    if (enclosed)
                        buffer.append('|');
                    else
                        buffer.append(',');
                    break;
                default:
                    buffer.append(c);
            }
        }
        return buffer.toString();
    }
}
