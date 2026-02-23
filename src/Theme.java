import java.awt.Color;

public final class Theme {
    public final Color bg;
    public final Color surface;
    public final Color surfaceAlt;
    public final Color border;
    public final Color text;
    public final Color muted;
    public final Color accent;
    public final Color accentBorder;
    public final Color start;
    public final Color due;
    public final Color currentDayBg;

    private Theme(
            Color bg,
            Color surface,
            Color surfaceAlt,
            Color border,
            Color text,
            Color muted,
            Color accent,
            Color accentBorder,
            Color start,
            Color due,
            Color currentDayBg
    ) {
        this.bg = bg;
        this.surface = surface;
        this.surfaceAlt = surfaceAlt;
        this.border = border;
        this.text = text;
        this.muted = muted;
        this.accent = accent;
        this.accentBorder = accentBorder;
        this.start = start;
        this.due = due;
        this.currentDayBg = currentDayBg;
    }

    public static final Theme LIGHT = new Theme(
            new Color(245, 247, 251),
            new Color(255, 255, 255),
            new Color(237, 241, 248),
            new Color(214, 221, 232),
            new Color(33, 37, 41),
            new Color(110, 118, 129),
            new Color(76, 111, 255),
            new Color(61, 94, 238),
            new Color(0, 150, 120),
            new Color(230, 73, 69),
            new Color(255, 255, 255)
    );

    public static final Theme DARK = new Theme(
            new Color(18, 20, 28),
            new Color(28, 31, 43),
            new Color(36, 40, 56),
            new Color(58, 64, 86),
            new Color(230, 235, 245),
            new Color(166, 175, 196),
            new Color(130, 170, 255),
            new Color(106, 142, 255),
            new Color(71, 200, 170),
            new Color(255, 120, 120),
            new Color(52, 60, 90)
    );

    public static Theme current = LIGHT;
}
