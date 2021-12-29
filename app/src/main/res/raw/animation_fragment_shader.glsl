precision mediump float;

uniform sampler2D u_TextureUnit;
varying vec2 v_TextureCoordinates;
uniform highp float alpha;
uniform vec2 inputSize;
uniform highp float progress;
uniform highp float blurSize;

#define num 7

vec4 directionBlur(sampler2D tex, vec2 uv, vec2 direction, float intensity)
{
    vec2 pixelStep = 1.0/inputSize * intensity;
    float dircLength = length(direction);
    pixelStep.x = direction.x * 1.0 / dircLength * pixelStep.x;
    pixelStep.y = direction.y * 1.0 / dircLength * pixelStep.y;

    vec4 color = vec4(0);
    for (int i = -num; i <= num; i++)
    {
        vec2 blurCoord = uv + pixelStep * float(i);
        vec2 uvT = vec2(1.0 - abs(abs(blurCoord.x) - 1.0), 1.0 - abs(abs(blurCoord.y) - 1.0));
        color += texture2D(tex, uvT);
    }
    color /= float(2 * num + 1);
    return color;
}

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
    if (blurSize == 0.0){
        gl_FragColor = texture2D(u_TextureUnit, uv)*alpha;
    } else {
        vec2 blurDirection = vec2(1.0, 0.0);
        vec4 resultColor = directionBlur(u_TextureUnit, uv, blurDirection, blurSize);
        gl_FragColor = resultColor*alpha;
    }

}