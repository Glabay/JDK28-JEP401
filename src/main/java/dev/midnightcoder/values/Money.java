/**
 * @author Glabay | The Midnight Coder
 * @project JDK28-JEP401
 * @social Discord: Glabay
 * @website <a href="https://midnightcoder.dev">Midnight Coder</a>
 * @since 2026-10-30
 */
public value class Money {

    private final long cents;
    private final String currency;

    public Money(long cents, String currency) {
        this.cents = cents;
        this.currency = currency;
    }

    public long cents() {
        return cents;
    }

    public String currency() {
        return currency;
    }
}

void main() {
    var price1 = new Money(1999, "CAD");
    var price2 = new Money(1999, "CAD");

    IO.println(price1.equals(price2));
    IO.println(price1 == price2);
}
