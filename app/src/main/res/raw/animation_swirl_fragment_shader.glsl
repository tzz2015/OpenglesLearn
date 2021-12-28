precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;
uniform highp float alpha;
uniform highp float progress;
uniform highp int direction;
uniform vec2 inputSize;





void main()
{
    vec2 uv = v_TextureCoordinates;
    //    gl_FragColor = texture2D(u_TextureUnit, swirl(uv))* step(uv.x, 2.0) * step(uv.y, 2.0) * step(-1.0, uv.x) * step(-1.0, uv.y);
    //    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates)*alpha;
    //获取旋转的直径
    float Res = float(min(inputSize.x, inputSize.y));
    vec2 st = uv;
    if (st.x>1.0){
        st.x=2.0-st.x;
    }
    if (st.x<0.0){
        st.x=-st.x;
    }
    if (st.y>1.0){
        st.y=2.0-st.y;
    }
    if (st.y<0.0){
        st.y=-st.y;
    }

    //半径 = 直径 * 0.5;
    float Radius = Res * 0.5;
    //准备旋转处理的纹理坐标 = 纹理坐标 * 直径
    vec2 xy = Res * st;

    vec2 dxy = xy - vec2(Res/2.0, Res/2.0);
    //r
    float r = length(dxy);
    //抛物线递减因⼦子:(1.0-(r/Radius)*(r/Radius) )
    float angle = 60.0*(1.0 - progress);
    if (direction != 0){
        angle = -60.0 * progress;
    }
    float beta = atan(dxy.y, dxy.x) + radians(angle) * 2.0 * (1.0-(r/Radius)*(r/Radius));
    if (r <= Radius)
    {
        //获取的纹理坐标旋转beta度.
        xy = vec2(Res/2.0, Res/2.0) + r*vec2(cos(beta), sin(beta));
    }
    //st = 旋转后的纹理坐标/旋转范围
    st = xy/Res;
    //将旋转的纹理坐标替换原始纹理坐标TextureCoordsVarying 获取对应像素点的颜⾊.
    vec3 irgb = texture2D(u_TextureUnit, st).rgb;
    //将计算后的颜⾊填充到像素点中 gl_FragColor
    gl_FragColor = vec4(irgb, 1.0);

}