import type { CapacitorConfig } from '@capacitor/cli';

const config: CapacitorConfig = {
  appId: 'com.student.appnotes',
  appName: 'Gestion Notes',
  webDir: 'www',
  server: {
    androidScheme: 'https',
  },
};

export default config;
