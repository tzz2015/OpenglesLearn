precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;
uniform highp float alpha;


void main()
{
    vec2 uv = v_TextureCoordinates;
    if (uv.x>1.0){
        uv.x=2.0-uv.x;
    }
    if (uv.x<0.0){
        uv.x=-uv.x;
    }
    if (uv.y>1.0){
        uv.y=2.0-uv.y;
    }
    if (uv.y<0.0){
        uv.y=-uv.y;
    }
    gl_FragColor = texture2D(u_TextureUnit, uv)*alpha;
}