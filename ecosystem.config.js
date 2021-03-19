module.exports = {
  apps: [
    {
      name: 'aws-codedeploy-backend',
      script: 'java',
      args: '-jar build/libs/*',
      interpreter: 'none',
      env: {
        NODE_ENV: 'prod',
      },
    },
  ],
}
