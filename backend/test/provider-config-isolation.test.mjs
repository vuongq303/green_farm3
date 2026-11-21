import assert from 'node:assert/strict';
import { test } from 'node:test';
import { APP_REDIRECT_URI } from '../src/auth/config.js';
import { loadSocialAuthConfig } from '../src/config.js';

const AUTH_ORIGIN = 'https://accounts.example.test';
const GOOGLE = {
  GOOGLE_OAUTH_CLIENT_ID: 'isolation-fixture.apps.googleusercontent.com',
  GOOGLE_OAUTH_CLIENT_SECRET: 'valid-google-fixture-secret',
};
const FACEBOOK = {
  FACEBOOK_APP_ID: '123456789',
  FACEBOOK_APP_SECRET: 'valid-facebook-fixture-secret',
};

function disabled(provider) {
  assert.equal(provider.enabled, false);
  assert.equal(provider.configurationError, 'oauth_not_configured');
  assert.equal(provider.clientId, '', 'an unusable OAuth identity must not survive validation');
  assert.equal(provider.clientSecret, '', 'an unusable OAuth secret must not survive validation');
}

function websiteEnabled(config) {
  assert.equal(config.publicBaseUrl, AUTH_ORIGIN);
  assert.equal(config.providers.password.enabled, true);
  assert.equal(config.redirectUri, APP_REDIRECT_URI);
}

test('website configuration needs no Facebook or Google credentials', () => {
  const config = loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: AUTH_ORIGIN });
  websiteEnabled(config);
  disabled(config.providers.facebook);
  disabled(config.providers.google);
});

const badFacebook = [
  ['missing secret', { FACEBOOK_APP_ID: FACEBOOK.FACEBOOK_APP_ID }],
  ['missing app ID', { FACEBOOK_APP_SECRET: 'private-fb-secret-without-id' }],
  ['invalid app ID', { ...FACEBOOK, FACEBOOK_APP_ID: 'not-a-facebook-app-id' }],
  ['invalid graph version', { ...FACEBOOK, FACEBOOK_GRAPH_VERSION: 'v24.1' }],
  ['whitespace secret', { ...FACEBOOK, FACEBOOK_APP_SECRET: 'private-fb secret-with-space' }],
  ['non-string secret', { ...FACEBOOK, FACEBOOK_APP_SECRET: { privateFixture: 'not-a-string' } }],
  ['oversized secret', { ...FACEBOOK, FACEBOOK_APP_SECRET: 'private-fb-long-secret'.repeat(100) }],
  ['oversized app ID', { ...FACEBOOK, FACEBOOK_APP_ID: '1'.repeat(513) }],
];

for (const [description, invalid] of badFacebook) {
  test(`Facebook ${description} disables only Facebook, keeping website and configured Google available`, () => {
    const config = loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: AUTH_ORIGIN, ...GOOGLE, ...invalid });
    websiteEnabled(config);
    disabled(config.providers.facebook);
    assert.equal(config.providers.google.enabled, true);
    assert.equal(config.providers.google.clientId, GOOGLE.GOOGLE_OAUTH_CLIENT_ID);
    assert.equal(config.providers.google.clientSecret, GOOGLE.GOOGLE_OAUTH_CLIENT_SECRET);
    if (typeof invalid.FACEBOOK_APP_SECRET === 'string') {
      assert.ok(!JSON.stringify(config.providers.facebook).includes(invalid.FACEBOOK_APP_SECRET),
        'disabled-provider diagnostics must not expose a supplied secret');
    }
  });
}

const badGoogle = [
  ['missing secret', { GOOGLE_OAUTH_CLIENT_ID: GOOGLE.GOOGLE_OAUTH_CLIENT_ID }],
  ['missing client ID', { GOOGLE_OAUTH_CLIENT_SECRET: 'private-google-secret-without-id' }],
  ['invalid secret', { ...GOOGLE, GOOGLE_OAUTH_CLIENT_SECRET: 'private-google\nsecret' }],
  ['non-string client ID', { ...GOOGLE, GOOGLE_OAUTH_CLIENT_ID: 12345 }],
];

for (const [description, invalid] of badGoogle) {
  test(`Google ${description} keeps website and configured Facebook available`, () => {
    const config = loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: AUTH_ORIGIN, ...FACEBOOK, ...invalid });
    websiteEnabled(config);
    disabled(config.providers.google);
    assert.equal(config.providers.facebook.enabled, true);
    assert.equal(config.providers.facebook.clientId, FACEBOOK.FACEBOOK_APP_ID);
    assert.equal(config.providers.facebook.clientSecret, FACEBOOK.FACEBOOK_APP_SECRET);
    assert.equal(config.providers.facebook.version, 'v24.0');
  });
}

test('both broken OAuth configurations leave independent website authentication enabled', () => {
  const config = loadSocialAuthConfig({
    AUTH_PUBLIC_BASE_URL: AUTH_ORIGIN,
    FACEBOOK_APP_SECRET: 'private-fb-incomplete',
    GOOGLE_OAUTH_CLIENT_SECRET: 'private-google-incomplete',
  });
  websiteEnabled(config);
  disabled(config.providers.facebook);
  disabled(config.providers.google);
});

test('valid OAuth configurations retain their credentials and configured Facebook graph version', () => {
  const config = loadSocialAuthConfig({
    AUTH_PUBLIC_BASE_URL: AUTH_ORIGIN + '/',
    AUTH_TRUSTED_PROXY_IP: '127.0.0.1',
    ...GOOGLE,
    ...FACEBOOK,
    FACEBOOK_GRAPH_VERSION: 'v25.0',
  });
  websiteEnabled(config);
  assert.equal(config.providers.google.enabled, true);
  assert.equal(config.providers.google.clientSecret, GOOGLE.GOOGLE_OAUTH_CLIENT_SECRET);
  assert.equal(config.providers.facebook.enabled, true);
  assert.equal(config.providers.facebook.clientSecret, FACEBOOK.FACEBOOK_APP_SECRET);
  assert.equal(config.providers.facebook.version, 'v25.0');
  assert.equal(config.trustedProxyIp, '127.0.0.1');
});

test('website configuration accepts valid HTTPS hostname and IP origins independently of OAuth', () => {
  for (const origin of ['https://localhost', 'https://localhost:8443/', 'https://127.0.0.1',
    'https://0.0.0.0:443/', 'https://[::1]:9443', 'https://nôngtrại.example',
    'HTTPS://accounts.example.test:8443/', 'https://fixture-account.trycloudflare.com']) {
    const config = loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: origin, FACEBOOK_APP_ID: 'incomplete' });
    assert.equal(config.publicBaseUrl, new URL(origin).origin);
    assert.equal(config.providers.password.enabled, true);
    disabled(config.providers.facebook);
  }
});

test('optional-provider isolation never enables website authentication without a valid HTTPS origin', () => {
  const missing = loadSocialAuthConfig({ ...GOOGLE, ...FACEBOOK });
  assert.equal(missing.publicBaseUrl, null);
  assert.equal(missing.providers.password.enabled, false);
  for (const origin of ['http://accounts.example.test', 'http://127.0.0.1:8787', 'https://127.1',
    'https://user:private-origin-value@accounts.example.test', 'https://accounts.example.test/path',
    'https://accounts.example.test/?token=private-origin-value']) {
    assert.throws(() => loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: origin }), error =>
      error.message.includes('AUTH_PUBLIC_BASE_URL') && !error.message.includes('private-origin-value'));
  }
  assert.throws(() => loadSocialAuthConfig({ AUTH_PUBLIC_BASE_URL: AUTH_ORIGIN,
    AUTH_TRUSTED_PROXY_IP: '127.0.0.1/8' }), /AUTH_TRUSTED_PROXY_IP/u);
});
