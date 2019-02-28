public class BaseParser
{
    private String buffer;
    private int current_pos = 0;

    public BaseParser(String input)
    {
        buffer = input;
    }

    protected  char at(int index)
    {
        return (index < buffer.length())? buffer.charAt(index) : '\0';
    }

    public char current()
    {
        return at(current_pos);
    }

    public boolean end()
    {
        return current() == '\0';
    }

    public void next()
    {
        if(!end()) ++current_pos;
    }

    public void skipEmptyCharacters()
    {
        while(Character.isWhitespace(current())) next();
    }

    public String matchNoExcept(String[] tokens)
    {
        int pos = current_pos;
        for (String token: tokens)
        {
            boolean match = true;
            for (char c: token.toCharArray())
            {
                if(current() == c) next();
                else
                {
                    current_pos = pos;
                    match = false;
                    break;
                }
                if(match)
                {
                    skipEmptyCharacters();
                    return token;
                }
            }
        }
        return null;
    }

    public String match(String[] tokens) throws UnexpectedTokenException
    {
        String res = matchNoExcept(tokens);
        if (res == null)
        {
            String msg = String.format("В позициии %d ожидалось: ", current_pos + 1);
            for(String t : tokens) msg += ("'" + t + "' ");
            throw new UnexpectedTokenException(msg);
        }
        return res;
    }

    public String match(String token) throws UnexpectedTokenException
    {
        return match(new String[]{token});
    }

    public boolean isMatch(String[] tokens)
    {
        int pos = current_pos;
        String res = matchNoExcept(tokens);
        current_pos = pos;
        return res != null;
    }

    public boolean isMatch(String token)
    {
        return isMatch(new String[]{token});
    }

    public class UnexpectedTokenException extends Exception
    {
        UnexpectedTokenException(String msg) {super(msg);}
    }
}
