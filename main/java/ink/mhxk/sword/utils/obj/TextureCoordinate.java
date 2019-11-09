package ink.mhxk.sword.utils.obj;

/**
 * 鏉ヨ嚜鎷斿垁鍓戠殑寮�婧愪唬鐮�
 * https://github.com/flammpfeil/SlashBlade/tree/1.12.2
 */
public final class TextureCoordinate {
    public float u, v, w;

    public TextureCoordinate(float u, float v) {
        this(u, v, 0F);
    }

    public TextureCoordinate(float u, float v, float w) {
        this.u = u;
        this.v = v;
        this.w = w;
    }
}
