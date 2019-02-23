// зря чтоли грамматики учил
//   E    :-   T1 + E   |  T1 - E  |  T1    # аддетивные операции - низший приоритет
//   T1   :-   T2 * T1  |  T2 / T1 |  T2    # мультипликативные операции - чуть приоритетнее
//   T2   :-  -T3       |  T3               # унарные операторы - ещё приоритетнее
//   T3   :-   N        |  (E)              # самый топ - скобки
//TODO: подумать про тернарные операторы

// никаких деревьев. сразу считаем.
public class MathParser extends BaseParser
{
    public MathParser(String input)
    {
        super(input);
    }

    // получение N токена
    protected double N()
    {
        String buf = "";
        while(current() =='.' || Character.isDigit(current()))
        {
            buf += current();
            next();
        }
        if(buf.length() == 0) return 0;

        skipEmptyCharacters();
        return Double.parseDouble(buf);
    }

    protected double T3()
    {
        if(isMatch("("))
        {
            match("(");
            double res = E();
            match(")");
            return res;
        }
        return N();
    }

    protected double T2()
    {
        if(isMatch("-"))
        {
            match("-");
            return -1 * T3();
        }
        return T3();
    }

    protected double T1()
    {
        double res = T2();
        String[] tokens = {"*", "/"};
        while(isMatch(tokens))
        {
            String op = match(tokens);
            double t1 = T1();
            res = op == "*"? res * t1 : res / t1;
        }
        return res;
    }

    protected double E()
    {
        double res = T1();
        String[] tokens = {"+", "-"};
        while(isMatch(tokens))
        {
            String op = match(tokens);
            double e = E();
            res = op == "+"? res + e : res - e;
        }
        return res;
    }

    public double result()
    {
        return E();
    }

    public static double Calculate(String input)
    {
        MathParser mp = new MathParser(input);
        return mp.result();
    }
}
