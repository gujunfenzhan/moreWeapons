package ink.mhxk.sword.utils.obj;

/**
 * 鏉ヨ嚜鎷斿垁鍓戠殑寮�婧愪唬鐮�
 * https://github.com/flammpfeil/SlashBlade/tree/1.12.2
 */
public final class Vertex {
    public float x, y, z;

    public Vertex(float x, float y) {
        this(x, y, 0F);
    }

    public Vertex(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
