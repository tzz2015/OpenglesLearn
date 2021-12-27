precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;
uniform highp float alpha;


void main()
{
    gl_FragColor = texture2D(u_TextureUnit, v_TextureCoordinates)*alpha;
}